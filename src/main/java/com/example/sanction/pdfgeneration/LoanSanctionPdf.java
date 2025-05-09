package com.example.sanction.pdfgeneration;

import java.time.LocalDate;

import javax.persistence.Column;

import org.hibernate.annotations.UpdateTimestamp;

import lombok.Data;

@Data
public class LoanSanctionPdf 
{
	private Integer sanctionId;
	
	private String applicantName;
	
	private Long contactNo;
	
	private Double loanSanctionedAmount;
	
	private Double monthlyEmiAmount;
	
	private Float rateOfInterest;
	
	private Integer loanTenureInMonth;
	
	private Long requestedLoanAmount;
	
	private Double NetMonthlyIncome;
	
	private Double processingFees;  
	
	@UpdateTimestamp
	@Column(name = "PDF_Created_Date")
	private LocalDate date;
}
