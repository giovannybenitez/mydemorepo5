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

import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.service.IBusinessService;
import com.example.util.BusinessLogic;
import com.example.util.DateUtil;

@SpringBootApplication
@ComponentScan(basePackages = {"com.example"})
@EntityScan("com.example.model")
@EnableJpaRepositories("com.example.repository")
public class Application implements CommandLineRunner{
	
	@Autowired
	private IBusinessService iBusinessService;
	
	@Autowired
	private BusinessLogic businessLogic;
	
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
		//this.calculateInstallmentValue();
		
		//Method #1.2
		//this.changeUserTarget();
		
		//Method #2
		//this.getLoansByDateFilter();
		
		//Method #3
		this.registerPayment();
		
	}
	
	public void createLoanRequest() {
		LoanResponse lr = iBusinessService.createLoanRequest(new LoanRequest(550000, 12, Long.valueOf(1)));
		log.info("Solicitud de prestamo aceptada id: {} y valor de la cuota mensual {}", lr.getLoanId(), lr.getInstallment());
	}
	
	public void calculateInstallmentValue() {
		double i = businessLogic.calculateInstallmentValue(1000, 12, 0.05);
		log.info("Valor de la cuota a pagar: {} ", i);
	}
	
	public void changeUserTarget() {
		iBusinessService.changeUserTarget(Long.valueOf(1));
	}
	
	public void getLoansByDateFilter() {
		
		List<Loan> loans = iBusinessService.getLoansByDateFilter(
				dateUtil.convertStringDateToDate("2019-01-01T00:00:00"),
				dateUtil.convertStringDateToDate("2019-12-31T23:59:59") , 0, 5);
		
		log.info("Listado de prestamos por fecha: {} ", loans);
	}
	
	public void registerPayment() {
		
		//Escenario 1: monto 0 OK
		//Escenario 2: Prestamo cerrado OK
		//Escenario 3: monto mayor a la deuda OK
		//Escenario 4: probar con varios pagos que esten en base de datos
		//PaymentResponse con valores para todos los escenarios
		PaymentResponse pr = iBusinessService.registerPayment(new Payment(Long.valueOf(4), 85, new Date()));
		log.info("Respuesta del pago realizado: {} ", pr);
	}
	
	


}
