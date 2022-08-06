package com.example.util;

import java.util.List;

import org.springframework.stereotype.Component;

import com.example.model.Loan;
import com.example.model.Payment;
import com.example.model.Target;

@Component
public class BusinessLogic {
	
	enum eTarget { NEW, FREQUENT, PREMIUM}
	
	public double calculateInstallmentValue(double amount, int term, double rate) {
		
//		BigDecimal rat = new BigDecimal(0.05);
//		BigDecimal r = rat.divide(new BigDecimal(term), 3, RoundingMode.HALF_UP);
//		
//		double x = (1+r.doubleValue());
//		double y = term - 1;
//		
//		BigDecimal num1 = new BigDecimal(x);
//		BigDecimal a = num1.pow((int) y);
		
		Double r = (rate / term);//0.004166666666666667
//		
//		
//		
		Double a = r + r; //0.008333333333333333
		Double b = Math.pow((1+r), term - 1); //1.0468002302556678
		Double c = (a / b);
		
		Double installment = c*1000*term-1;
		
		//                    [ r + r / ( (1+r) ^ term - 1) ]          x amount
		//double installment = ( r + r / ( Math.pow((1+r), term - 1) )) * amount;

		return installment;
	}

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
