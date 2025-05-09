package com.example.sanction.config;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.example.sanction.dto.LoanSanctionMailDTO;

@FeignClient(name = "mail-service")
public interface MailSanctionFeignApi 
{
	@PostMapping(value = "/api/sanction")
	public ResponseEntity<String> loanSanctionMail(@RequestBody LoanSanctionMailDTO loanSanctionMailDTO);
}
