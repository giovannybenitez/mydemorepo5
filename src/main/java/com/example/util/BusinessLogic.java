package com.example.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

import org.springframework.stereotype.Component;

import com.example.model.Loan;
import com.example.model.Payment;
import com.example.model.Target;

@Component
public class BusinessLogic {
	
	enum eTarget { NEW, FREQUENT, PREMIUM}
	
	/**
	 * Retorna la cuota mensual sugún el monto, el plazo y la tasa de interés
	 * @param amount
	 * @param term
	 * @param rate
	 * @return installment valor cuota mensual
	 */
	public double calculateInstallmentValue(double amount, int term, double rate) {
		
		
		Double r = rate / (term);//0.004166666666666667
		
//		Double a = (r + r) ; //0.008333333333333333
//		Double b = Math.pow((1+r), (term-1)); //1.0468002302556678 //Espero 0.097
//		Double c = (a / b); //  0.0856 esto es lo que esperaria
//		Double installment = c * amount;
		
		// Formula original  [ r + r / ( (1+r) ^ term - 1) ]             x amount
		Double installment = ( r + r / ( Math.pow((1+r), (term - 1) ) )) * amount;
		
		BigDecimal bd = new BigDecimal(installment).setScale(2, RoundingMode.HALF_UP);

		return bd.doubleValue();
	}

	
	/**
	 * Calcula el nuevo target dado algunos parametros
	 * @param loansCount Cantidad de prestamos
	 * @param amountTotal Cantidad total de prestamos
	 * @param currentTarget Target actual
	 * @param targetList Lista de targets para conocer sus valores permitidos y poder realizar
	 * algunos calculos
	 * @return String nuevo target
	 */
	public String getNewTarget(int loansCount, double amountTotal, String currentTarget, List<Target> targetList) {
		
		Target tNew = null;
		Target tFrequent = null;
		Target tPremium = null;
		
		for (Target target : targetList) {
			if(target.getType().equals(eTarget.NEW.toString())) {
				tNew = target;
			}
			if(target.getType().equals(eTarget.FREQUENT.toString())) {
				tFrequent = target;
			}
			if(target.getType().equals(eTarget.PREMIUM.toString())) {
				tPremium = target;
			}
		}
		
		String newTarget = currentTarget;
		
		if(loansCount > tNew.getMinLoanCount() && loansCount < tNew.getMaxLoanCount() && amountTotal < tNew.getMaxLoanAllowed()) {
			newTarget = eTarget.NEW.toString();
			
		} else if(loansCount >= tFrequent.getMinLoanCount() && loansCount <= tFrequent.getMaxLoanCount() 
				&& amountTotal >= tFrequent.getMinLoanAllowed() && amountTotal <= tFrequent.getMaxLoanAllowed()) {
			newTarget = eTarget.FREQUENT.toString();
			
		} else if(loansCount > tPremium.getMinLoanCount() && amountTotal > tPremium.getMinLoanAllowed()) {
			newTarget = eTarget.PREMIUM.toString();
			
		}
		
		return newTarget;
	}

	/**
	 * Retorna la deuda real de un prestamo
	 * @param loan
	 * @param payments
	 * @return
	 */
	public double getLoanDebt(Loan loan, List<Payment> payments) {
		
		if(loan.getStatus().equals("CLOSED")) {
			return 0;
		}
		
		if(payments.size() == 0) {
			return loan.getAmount();
		}
		
		
		double totalPaid = 0;
		for (Payment payment : payments) {
			totalPaid += payment.getAmount();
		}
		
		double debt = loan.getAmount() - totalPaid;
		
		return debt;
	}

}
