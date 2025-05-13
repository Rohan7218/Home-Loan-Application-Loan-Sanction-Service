package com.example.sanction.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class ModeOfPaymentDTO 
{
	@Enumerated(EnumType.STRING)
	private ModeOfPaymentEnum modeOfPayment;  
}
