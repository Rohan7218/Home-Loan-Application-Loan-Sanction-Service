package com.example.sanction.rest;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;


import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.sanction.dto.LoanSanctionDTO;
import com.example.sanction.response.ApiResponse;
import com.example.sanction.service.LoanSanctionService;

@RestController
@RequestMapping(value = "/api/loansanction")
public class LoanSanctionController 
{
	
	private static final Logger LOGGER=LoggerFactory.getLogger(LoanSanctionController.class);
	
	@Autowired
	private LoanSanctionService loanSanctionService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<String>> addSanctionDetails(@RequestBody LoanSanctionDTO loanSanctionDTO)
	{
		LOGGER.info("LoanSanctionController : PostMapping : addSanctionDetails : Entry");
		String msg=loanSanctionService.addSanctionDetails(loanSanctionDTO);
		ApiResponse<String> apiResponse=new ApiResponse<String>(msg);
		LOGGER.info("LoanSanctionController : PostMapping : addSanctionDetails : Entry");
		return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.CREATED);
	}
	
	@PostMapping(value = "/{sanctionId}")
	public ResponseEntity<ApiResponse<String>> calculateEligibleLoanAmount(@PathVariable Integer sanctionId)
	{
		LOGGER.info("LoanSanctionController : PostMapping : calculateEligibleLoanAmount : Entry");
		String msg=loanSanctionService.calculateEligibleLoanAmount(sanctionId);
		ApiResponse<String> apiResponse=new ApiResponse<String>(msg);
		LOGGER.info("LoanSanctionController : PostMapping : calculateEligibleLoanAmount : Exit");
		return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.CREATED);
	}
	
	
	@GetMapping(value = "/{sanctionId}")
	public ResponseEntity<ApiResponse<Object>> getMonthlyEmi(@PathVariable Integer sanctionId)
	{
		Object emi=loanSanctionService.getMonthlyEmi(sanctionId);
		ApiResponse<Object> apiResponse=new ApiResponse<Object>(emi);
		return new ResponseEntity<ApiResponse<Object>>(apiResponse, HttpStatus.OK);
	}
	
}
