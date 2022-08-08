package com.example.app;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import com.example.model.Debt;
import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.model.Target;
import com.example.service.IBusinessService;
import com.example.util.DateUtil;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repository")
public class Application implements CommandLineRunner{
	
	@Autowired
	private IBusinessService iBusinessService;
	
	@Autowired
	private DateUtil dateUtil;
	
	private static Logger log = LoggerFactory.getLogger(Application.class);

	protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
		return builder.sources(Application.class);
    }
	
	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		
		//Method #1
		//this.createLoanRequest();
		
		//Method #1.1
		//this.changeUserTarget();
		
		//Method #1.2
		//this.changeTargetParams();
		
		//Method #2
		//this.getLoansByDateFilter();
		
		//Method #3
		//this.registerPayment();
		
		//Method #4
		//this.getDebtByLoan();
		
		//Method #5
		//this.getDebtByOpenLoans();
		
		//Method #6
		//this.getDebtByTarget();
		
	}
	

	public void createLoanRequest() {
		LoanResponse lr = iBusinessService.createLoanRequest(new LoanRequest(550000, 12, Long.valueOf(2)));
		log.info("Solicitud de prestamo aceptada id: {}, valor de la cuota mensual: {}, Mensaje: {} ", lr.getLoanId(), lr.getInstallment(), lr.getMessage());
	}
	
	public void changeUserTarget() {
		iBusinessService.changeUserTarget(Long.valueOf(1));
	}
	
	public void changeTargetParams() {
		Target t = new Target("NEW", 0, 2, 0, 100000, 0.16, 500000);
		t.setTargetId(Long.valueOf(1));
		iBusinessService.changeTargetParams(t);
	}
	
	public void getLoansByDateFilter() {
		
		List<Loan> loans = iBusinessService.getLoansByDateFilter(
				dateUtil.convertStringDateToDate("2019-01-01T00:00:00"),
				dateUtil.convertStringDateToDate("2019-12-31T23:59:59") , 0, 5);
		
		log.info("Listado de prestamos por fecha: {} ", loans);
	}
	
	public void registerPayment() {
		
		PaymentResponse pr = iBusinessService.registerPayment(new Payment(Long.valueOf(4), 85, new Date()));
		log.info("Respuesta del pago realizado: {} ", pr.getId());
	}
	
	public void getDebtByLoan() {
		Debt debt = iBusinessService.getDebtByLoan(Long.valueOf(4));
		log.info("Deuda por pago: {} ", debt.getBalance());
	}
	
	public void getDebtByOpenLoans() {
		Debt debt = iBusinessService.getDebtByOpenLoans();
		log.info("Deuda prestamos abiertos: {} ", debt.getBalance());
	}
	
	public void getDebtByTarget() {
		String target = "NEW";
		Debt debt = iBusinessService.getDebtByTarget(target);
		log.info("Deuda prestamos con target {} es: {} ", target, debt.getBalance());
	}
	
	


}
