package com.example.controller;

import java.util.List;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.model.Loan;
import com.example.model.LoanRequest;
import com.example.model.LoanResponse;
import com.example.model.Payment;
import com.example.model.PaymentResponse;
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
	
	
	
}