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
	
	
	/**
	 * Este Método crea una solicitud de prestamo en base de datos, dado unos parametros iniciales
	 * 
	 */
	@Transactional
	@Override
	public LoanResponse createLoanRequest(LoanRequest loanRequest) {
		
		Customer customer = null;
		Target target = null;
		LoanResponse loanResponse = null;
		
		try {
			
			customer = iCustomerRepository.findById(loanRequest.getUserId()).orElseThrow(() -> new Exception());
			
			target = iTargetRepository.findByType(customer.getTarget());
			
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
	public List<Loan> getLoansByDateFilter(Date startDate, Date endDate, int page, int size) {
		
		Pageable pageable = PageRequest.of(page, size);
		
		List<Loan> loans = null;
		
		try {
			//loans = iLoanRepository.findByDateFilter(startDate, endDate);
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
			
			if(payment.getAmount() == 0) {
				log.info("Monto no permitido. {}, {}", payment.getAmount(), new Date());
				paymentResponse.setMessage("Monto no permitido. Valor cero");
			}else {
				
				List<Payment> payments = iPaymentRepository.findByLoan(payment.getLoanId());
				
				Loan loan = iLoanRepository.findById(payment.getLoanId()).orElseThrow(() -> new Exception());
				
				double debt = businessLogic.getLoanDebt(loan, payments);
				
				if(debt == 0 || loan.getStatus().equals("CLOSED")) {
					log.info("El prestamo con el id {} se encuentra pagado en su totalidad. {} ",loan.getLoanId(), new Date());
					paymentResponse.setMessage("Prestamo en estado cerrado o cancelado");
					
				}else {
					if(payment.getAmount() > debt) {
						log.info("Monto a pagar {} no puede ser superior a la deuda. {}, {}", payment.getAmount(), debt, new Date());
						paymentResponse.setMessage("Monto no permitido. supera el valor de la deuda");
						
					}else {
						payment.setDate(new Date());
						payment = iPaymentRepository.save(payment);
						
						double debtAfterPayment = debt - payment.getAmount();
						
						if(debtAfterPayment == 0) {
							loan.setStatus("CLOSED");
							iLoanRepository.save(loan);
						}
						
						paymentResponse.setId(payment.getPaymentId());
						paymentResponse.setLoanId(payment.getLoanId());
						paymentResponse.setDebt(debtAfterPayment);
						paymentResponse.setMessage("Pago realizado exitasamente.");
						
						log.info("Pago realizado correctamente Id: {} Valor: {} Prestamo id: {} Fecha: {}", payment.getPaymentId(), payment.getAmount(), payment.getLoanId(), new Date() );
					}
				}
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			log.error("Error registering payment", e);
		}
		
		return paymentResponse;
		
	}
	
	@Override
	public void changeUserTarget(Long userId) {
		
		double amountTotal = 0;
		
		try {
			Customer customer = iCustomerRepository.findById(userId).orElseThrow(() -> new Exception());
			log.info("Target anterior {} ", customer.getTarget());
			
			List<Loan> loans = iLoanRepository.findByUser(customer.getCustomerId());
			
			for (Loan loan : loans) {
				amountTotal += loan.getAmount();
			}
			
			List<Target> targetList = iTargetRepository.findAll();
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
	public Target getTargetByType(String type) {
		
		Target target = iTargetRepository.findByType(type);
		
		return target;
		
	}

}
