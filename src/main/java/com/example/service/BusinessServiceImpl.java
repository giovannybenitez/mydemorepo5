package com.example.service;

import java.util.Date;
import java.util.List;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.example.model.Customer;
import com.example.model.Debt;
import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.model.Target;
import com.example.repository.ICustomerRepository;
import com.example.repository.ILoanRepository;
import com.example.repository.IPaymentRepository;
import com.example.repository.ITargetRepository;
import com.example.util.BusinessLogic;

/**
 * @author gbenitez
 *
 */
@Service
public class BusinessServiceImpl implements IBusinessService{
	
	private static Logger log = LoggerFactory.getLogger(BusinessServiceImpl.class);
	
	@Autowired
	private ICustomerRepository iCustomerRepository;
	
	@Autowired
	private ITargetRepository iTargetRepository;
	
	@Autowired
	private ILoanRepository iLoanRepository;
	
	@Autowired
	private IPaymentRepository iPaymentRepository;
	
	@Autowired
	private BusinessLogic businessLogic;
	
	
	
	@Transactional
	@Override
	public LoanResponse createLoanRequest(LoanRequest loanRequest) {
		
		Customer customer = null;
		Target target = null;
		LoanResponse loanResponse = null;
		
		try {
			
			try {
				customer = iCustomerRepository.findById(loanRequest.getUserId()).orElseThrow(() -> new Exception());
			} catch (Exception e) {
				log.error("User no found", e);
				return new LoanResponse("1", "Usuario no existe" + loanRequest.getUserId());
			}
			
			target = iTargetRepository.findByType(customer.getTarget());
			
			if(loanRequest.getAmount() > target.getMaxAmount()) {
				return new LoanResponse("2", "Monto solicitado no permitido para usuario target: " + target.getType());
			}
			
			Loan loan = new Loan(loanRequest.getAmount(), loanRequest.getTerm(), target.getRate(), 
					customer.getCustomerId(), customer.getTarget(), new Date(), "OPEN");
			
			loan = iLoanRepository.save(loan);
			
			double installment = businessLogic.calculateInstallmentValue(loan.getAmount(), loan.getTerm(), loan.getRate());
			
			loanResponse = new LoanResponse(loan.getLoanId(), installment);
			log.info("Solicitud de prestamo aceptada id: {} y valor de la cuota mensual {}", loanResponse.getLoanId(), loanResponse.getInstallment());
			
			this.changeUserTarget(customer.getCustomerId());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error creating Loan request", e);
		}
		
		return loanResponse;
	}
	
	
	@Override
	public void changeUserTarget(Long userId) {
		
		double amountTotal = 0;
		
		try {
			Customer customer = iCustomerRepository.findById(userId).orElseThrow(() -> new Exception());
			log.info("Target actual {} ", customer.getTarget());
			
			List<Loan> loans = iLoanRepository.findByUser(customer.getCustomerId());
			
			for (Loan loan : loans) {
				amountTotal += loan.getAmount();
			}
			
			List<Target> targetList = iTargetRepository.findAll();
			
			//Calcula el nuevo target del usuario según parametros
			String newTarget = businessLogic.getNewTarget(loans.size(), amountTotal, customer.getTarget(), targetList);
			
			customer.setTarget(newTarget);
			iCustomerRepository.save(customer);
			
			log.info("Target nuevo {} ", customer.getTarget());
			log.info("Solicitud de cambio de target aceptada {} ", new Date());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error getting loan list", e);
		}
	}
	
	@Override
	public Target changeTargetParams(Target target) {
		try {
			
			target = iTargetRepository.save(target);
			
			log.info("Target actualizado correctamente {} ", new Date());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error updating target", e);
		}
		
		return target;
		
	}
	
	
	@Override
	public List<Loan> getLoansByDateFilter(Date startDate, Date endDate, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		List<Loan> loans = null;
		
		try {
			loans = (List<Loan>) iLoanRepository.findByDateFilter(startDate, endDate, pageable);
					
			log.info("Size {}", loans.size());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error getting loan list", e);
		}
		return loans;
	}
	
	@Transactional
	@Override
	public PaymentResponse registerPayment(Payment payment) {
		PaymentResponse paymentResponse = new PaymentResponse(payment.getLoanId());
		try {
			
			//Manejo de escenario #1 El usuario ingresa 0
			if(payment.getAmount() == 0) {
				log.info("Monto no permitido. {}, {}", payment.getAmount(), new Date());
				return new PaymentResponse("1", "Monto no permitido. Valor cero");
			}
			
			Loan loan;
			//Manejo de escenario #2 El prestamo no existe
			try {
				loan = iLoanRepository.findById(payment.getLoanId()).orElseThrow(() -> new Exception());
			} catch (Exception e) {
				log.error("Loan no found", e);
				return new PaymentResponse("2", "Prestamo no existe " + payment.getLoanId());
			}
			
			List<Payment> payments = iPaymentRepository.findByLoan(loan.getLoanId());
			
			double debt = businessLogic.getLoanDebt(loan, payments);
			
			//Manejo de escenario #3 Prestamo se  encuentra saldado
			if(debt == 0 || loan.getStatus().equals("CLOSED")) {
				log.info("El prestamo con el id {} se encuentra pagado en su totalidad. {} ",loan.getLoanId(), new Date());
				return new PaymentResponse("3", "Prestamo en estado cerrado o pagado en su totalidad.");
			}
			
			//Manejo de escenario #4 El usuario ingresa un monto superior a la deuda actual
			if(payment.getAmount() > debt) {
				log.info("Monto a pagar {} no puede ser superior a la deuda. {}, {}", payment.getAmount(), debt, new Date());
				return new PaymentResponse("4", "Monto no permitido. supera el valor de la deuda");
			}
			
			payment.setDate(new Date());
			payment = iPaymentRepository.save(payment);
			
			double debtAfterPayment = debt - payment.getAmount();
			
			//Si la deuda es cero ce cambia el estado del prestamo a cerrado
			if(debtAfterPayment == 0) {
				loan.setStatus("CLOSED");
				iLoanRepository.save(loan);
			}
			
			paymentResponse.setId(payment.getPaymentId());
			paymentResponse.setLoanId(payment.getLoanId());
			paymentResponse.setDebt(debtAfterPayment);
			
			log.info("Pago realizado correctamente Id: {} Valor: {} Prestamo id: {} Fecha: {}",
					payment.getPaymentId(), payment.getAmount(), payment.getLoanId(), new Date() );
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error registering payment", e);
		}
		
		return paymentResponse;
		
	}


	@Override
	public Debt getDebtByLoan(Long loanId) {
		
		Debt debt = null;
		
		try {
			Loan loan = iLoanRepository.findById(loanId).orElseThrow(() -> new Exception());
			List<Payment> payments = iPaymentRepository.findByLoan(loan.getLoanId());
			
			debt = new Debt(businessLogic.getLoanDebt(loan, payments));
			
			log.info("Deuda del prestamo: {} es: {}", loanId, debt.getBalance());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error getting debt by loan", e);
		}
		
		
		return debt;
	}
	
	
	@Override
	public Debt getDebtByOpenLoans(Date to) {
		Debt debt = null;
		
		try {
			List<Loan> loans = iLoanRepository.findByStatus("OPEN", to);
			
			double partialDebt = 0;
			for (Loan loan : loans) {
				List<Payment> payments = iPaymentRepository.findByLoan(loan.getLoanId());
				partialDebt += businessLogic.getLoanDebt(loan, payments);
			}
			debt = new Debt(partialDebt);
			
			log.info("Deuda total de todos los prestamos es: {}", debt.getBalance());
			
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error getting debt by loan", e);
		}
		
		
		return debt;
	}
	
	@Override
	public Debt getDebtByTarget(String target, Date to) {
		Debt debt = null;
		
		try {
			List<Loan> loans = iLoanRepository.findByTarget(target, to);
			
			double partialDebt = 0;
			for (Loan loan : loans) {
				List<Payment> payments = iPaymentRepository.findByLoan(loan.getLoanId());
				partialDebt += businessLogic.getLoanDebt(loan, payments);
			}
			debt = new Debt(partialDebt);
			
			log.info("Deuda total de todos los prestamos con Target {} es: {}", target, debt.getBalance());
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error getting debt by loan", e);
		}
		
		
		return debt;
	}
	

}
