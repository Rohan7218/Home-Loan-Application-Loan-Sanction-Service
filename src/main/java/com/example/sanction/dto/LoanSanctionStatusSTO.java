package com.example.sanction.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class LoanSanctionStatusSTO 
{
	@Enumerated(EnumType.STRING)
	private LoanSanctionStatusEnum loanSanctionStatusEnum; 
}
