package com.example.app;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.model.Target;
import com.example.service.IBusinessService;
import com.example.util.BusinessLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
class BusinessLogicTests {
	
	@Autowired
	private IBusinessService iBusinessService;
	
	@Autowired
	private BusinessLogic businessLogic;
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void createLoanRequestUserNotFound() {
		LoanResponse lr = iBusinessService.createLoanRequest(new LoanRequest(550000, 12, Long.valueOf(25)));
		Assert.assertEquals(lr.getErrorCode(),"1");
	}
	
	@Test
	public void createLoanRequestAmountNotAllowed() {
		LoanResponse lr = iBusinessService.createLoanRequest(new LoanRequest(550000, 12, Long.valueOf(1)));
		Assert.assertEquals(lr.getErrorCode(),"2");
	}
	
	@Test
	public void createLoanRequest() {
		LoanResponse lr = iBusinessService.createLoanRequest(new LoanRequest(2000, 12, Long.valueOf(1)));
		Assert.assertEquals(lr.getInstallment(), 46.81, 2);
	}
	
	@Test
	public void calculateInstallmentValue() {
		Assert.assertEquals(businessLogic.calculateInstallmentValue(1000, 12, 0.05), 97.76, 2);
	}
	
	@Test
	public void registerPaymentAmountZero() {
		PaymentResponse pr = iBusinessService.registerPayment(new Payment(Long.valueOf(4), 0, new Date()));
		Assert.assertEquals(pr.getErrorCode(),"1");
	}
	
	@Test
	public void registerPaymentLoanNotFound() {
		PaymentResponse pr = iBusinessService.registerPayment(new Payment(Long.valueOf(4000), 1000, new Date()));
		Assert.assertEquals(pr.getErrorCode(),"2");
	}
	
	@Test
	public void registerPaymentLoanPaid() {
		PaymentResponse pr = iBusinessService.registerPayment(new Payment(Long.valueOf(1), 1000, new Date()));
		Assert.assertEquals(pr.getErrorCode(),"3");
	}
	
	@Test
	public void registerPaymentHigherAmount() {
		PaymentResponse pr = iBusinessService.registerPayment(new Payment(Long.valueOf(27), 10000, new Date()));
		Assert.assertEquals(pr.getErrorCode(),"4");
	}
	
	@Test
	public void registerPayment() {
		PaymentResponse pr = iBusinessService.registerPayment(new Payment(Long.valueOf(27), 7000, new Date()));
		Assert.assertEquals(pr.getDebt(),0.0, 1);
		Assert.assertNotNull(pr.getLoanId());
	}
	
	@Test
	public void changeTargetParams() {
		Target t = new Target("NEW", 0, 2, 0, 100000, 0.16, 500000);
		t.setTargetId(Long.valueOf(1));
		
		t = iBusinessService.changeTargetParams(t);
		
		Assert.assertEquals(t.getType(), "NEW");
		Assert.assertNotEquals(t.getType(), "PREMIUM");
		Assert.assertEquals(t.getRate(), 0.16, 2);
		Assert.assertEquals(t.getMaxAmount(), 500000, 0);
	}
	
	@Test
	public void getNewTarget() {
		
		List<Target> list = new ArrayList<Target>();
		list.add(new Target("NEW", 0, 2, 0, 100000, 0.15, 500000));
		list.add(new Target("FREQUENT", 2, 5, 100000, 500000, 0.10, 1000000));
		list.add(new Target("PREMIUM", 5, 1000, 500000, 5000000, 0.05, 5000000));
		
		Assert.assertEquals(businessLogic.getNewTarget(1, 90000, "NEW", list) , "NEW");
		Assert.assertEquals(businessLogic.getNewTarget(3, 150000, "NEW", list) , "FREQUENT");
		Assert.assertEquals(businessLogic.getNewTarget(6, 600000, "NEW", list) , "PREMIUM");
		
		Assert.assertEquals(businessLogic.getNewTarget(1, 150000, "NEW", list) , "NEW");
		Assert.assertEquals(businessLogic.getNewTarget(3, 600000, "FREQUENT", list) , "FREQUENT");
		
		
	}

}

