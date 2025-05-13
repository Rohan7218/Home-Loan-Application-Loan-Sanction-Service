package com.example.sanction.dto;

import java.time.LocalDate;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import lombok.Data;

@Data
public class LoanDisbursmentDTO
{
	private Integer sanctionId;
	
	private LocalDate sanctionDate;
	
	private String applicantName;
	
	private Long contactNo;
	
	private String emailId;
	
	private Double loanSanctionedAmount;    
	
	private Float rateOfInterest;               
	
	private Integer loanTenureInMonth;      
	 
	private Double monthlyEmiAmount;             
	
	@Enumerated(EnumType.STRING)
	private ModeOfPaymentEnum modeOfPayment;  
	
	@Enumerated(EnumType.STRING)
	private LoanSanctionStatusEnum loanSanctionStatus;        	
	
	private Integer customerId;
	
	private Integer appllicantId;
	
	private Integer cibilScore;
	
	private Long requestedLoanAmount; 
	
	private Double NetMonthlyIncome;
	
	private String IFSC_Code;
	
	private Long accountNumber;
	
	private Double processingFees;                    
	
	@Enumerated(EnumType.STRING)
	private ProcessingFeesEnum processingFeesStatus;              
}
