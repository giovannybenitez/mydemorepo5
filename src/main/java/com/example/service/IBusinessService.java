package com.example.service;

import java.util.Date;
import java.util.List;

import com.example.model.Debt;
import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.model.Target;

public interface IBusinessService {
	
	/**
	 * Devuelve una solicitud de prestamo con su respectivo estado.
	 * @param loanRequest Objeto con los parametros (amount, term y userId)
	 * @return Respuesta de una solicitud de prestamo.
	 */
	public LoanResponse createLoanRequest(LoanRequest loanRequest);
	
	/**
	 * Modífica el target del usuario según la lógica de negocio.
	 * @param userId usuario actualizado
	 */
	public void changeUserTarget(Long userId);
	
	/**
	 * Modífica algunos parametros de un target
	 * @param target
	 * @return target Target actualizado.
 	 */
	public Target changeTargetParams(Target target);

	/**
	 * Devuelve un listado de prestamos según los filtros.
	 * @param startDate
	 * @param endDate
	 * @param page
	 * @param size
	 * @return List<Loan> Listado de prestamos.
	 */
	public List<Loan> getLoansByDateFilter(Date startDate, Date endDate, int page, int size);

	/**
	 * Registra un nuevo pago para un prestamo actual. 
	 * @param payment
	 * @return PaymentResponse Respuesta del pago realizado.
	 */
	public PaymentResponse registerPayment(Payment payment);

	/**
	 * Devuelve la deuda de un prestamo.
	 * @param loanId
	 * @return Debt Deuda.
	 */
	public Debt getDebtByLoan(Long loanId);

	/**
	 * Devuelve la deuda total de todos los prestamossegún fecha pasada como parametro.
	 * @param to
	 * @return Debt Deuda.
	 */
	public Debt getDebtByOpenLoans(Date to);

	/**
	 * Devuelve la deuda total de los prestamos según target y fecha pasados como parametros.
	 * @param target
	 * @param to
	 * @return
	 */
	public Debt getDebtByTarget(String target, Date to);

	

}
