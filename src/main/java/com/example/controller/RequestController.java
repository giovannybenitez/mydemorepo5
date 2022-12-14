package com.example.controller;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Debt;
import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
import com.example.model.Target;
import com.example.service.IBusinessService;
import com.example.util.DateUtil;


@RestController
public class RequestController {
	
	private static Logger log = LoggerFactory.getLogger(RequestController.class);
	
	@Autowired
	private IBusinessService iBusinessService;
	
	@Autowired
	private DateUtil dateUtil;
	
	
	@PostMapping(value = "/create-loan-request")
	public LoanResponse createLoanRequest(@RequestBody LoanRequest loanRequest) {
		
		LoanResponse lr = iBusinessService.createLoanRequest(loanRequest);
		log.info("Solicitud de prestamo aceptada id: {} y valor de la cuota mensual {}", lr.getLoanId(), lr.getInstallment());
		
		return lr;
	}
	
	@GetMapping(value = "/loans")
	public List<Loan> loans(@RequestBody String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		
		String from = (String) jsonObject.get("from");
		String to = (String) jsonObject.get("to");
		int page = (int) jsonObject.get("page");
		int size = (int) jsonObject.get("size");
		
		List<Loan> loans = iBusinessService.getLoansByDateFilter(
				dateUtil.convertStringDateToDate(from),
				dateUtil.convertStringDateToDate(to), page, size);;
		
		log.info("Listado de prestamos por rango de fecha {} - {}, {}", from, to, loans.size());

		
		return loans;
	}
	
	@PostMapping(value = "/register-payment")
	public PaymentResponse registerPayment(@RequestBody Payment payment) {
		
		PaymentResponse pr = iBusinessService.registerPayment(payment);
		log.info("Respuesta del pago realizado: {} ", pr.getId());
		
		return pr;
	}
	
	
	@GetMapping(value = "/debt-by-loan/{loanId}")
	public Debt debtByLoan(@PathVariable("loanId") Long loanId) {
		
		Debt debt = iBusinessService.getDebtByLoan(loanId);
		log.info("Deuda por pago: {} ", debt.getBalance());
		
		return debt;
	}
	
	@GetMapping(value = "/debt-by-all-loans")
	public Debt getDebtByOpenLoans(@RequestBody String body) {
		
		JSONObject jsonObject = new JSONObject(body);
		String date = (String) jsonObject.get("date");
		
		Debt debt = iBusinessService.getDebtByOpenLoans(dateUtil.convertStringDateToDate(date));
		log.info("Deuda: {} ", debt.getBalance());
		
		return debt;
	}
	
	@GetMapping(value = "/debt-by-target/{target}")
	public Debt debtByTarget(@RequestBody String body, @PathVariable("target") String target) {
		
		JSONObject jsonObject = new JSONObject(body);
		String date = (String) jsonObject.get("date");
		
		Debt debt = iBusinessService.getDebtByTarget(target, dateUtil.convertStringDateToDate(date));
		log.info("Deuda: {} ", debt.getBalance());
		
		return debt;
	}
	
	
	@PutMapping(value = "/change-target/{targetId}/{type}")
	public Target changeTargetParams(@RequestBody Target target, 
			@PathVariable("targetId") Long targetId, @PathVariable("type") String type) {
		
		target.setTargetId(targetId);
		target.setType(type);
		
		target = iBusinessService.changeTargetParams(target);
		log.info("Parametros del target {} configurados correctamente ", target.getType());
		
		return target;
		
	}
	
	
}