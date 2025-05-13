package com.example.sanction.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.sanction.dto.LoanDisbursmentDTO;

@FeignClient(name = "loan-disbursement-service")
public interface LoanDisbursmentFeignApi 
{
	@PostMapping(value = "/api/disbursment")
	public ResponseEntity<String> addDisbursment(@RequestBody LoanDisbursmentDTO loanDisbursmentDTO);
}
