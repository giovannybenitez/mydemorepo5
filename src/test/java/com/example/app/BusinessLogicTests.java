package com.example.app;

import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.example.model.Target;
import com.example.util.BusinessLogic;

@RunWith(SpringRunner.class)
@SpringBootTest
class BusinessLogicTests {
	
	@Autowired
	private BusinessLogic businessLogic;

	@Test
	void contextLoads() {
	}
	
	@Test
	public void calculateInstallmentValue() {
		
		Assert.assertEquals(businessLogic.calculateInstallmentValue(1000, 12, 0.05), 90.52,2);
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

