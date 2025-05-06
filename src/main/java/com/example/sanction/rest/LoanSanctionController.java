package com.example.sanction.rest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
	@Autowired
	private LoanSanctionService loanSanctionService;
	
	@PostMapping
	public ResponseEntity<ApiResponse<String>> addSanctionDetails(@RequestBody LoanSanctionDTO loanSanctionDTO)
	{
		String msg=loanSanctionService.addSanctionDetails(loanSanctionDTO);
		ApiResponse<String> apiResponse=new ApiResponse<String>(msg);
		return new ResponseEntity<ApiResponse<String>>(apiResponse, HttpStatus.CREATED);
	}
}
