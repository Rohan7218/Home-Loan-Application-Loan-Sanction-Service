package com.example.sanction.dto;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class UpdateProcessingFeesDTO 
{
	@Enumerated(EnumType.STRING)
	private ProcessingFeesEnum processingFeesEnum;
}
