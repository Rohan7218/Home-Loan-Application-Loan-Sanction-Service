package com.example.sanction.entity;

import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.DynamicUpdate;

import com.example.sanction.dto.LoanSanctionStatusEnum;
import com.example.sanction.dto.ModeOfPaymentEnum;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
@Entity
@DynamicUpdate
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "Loan_Sanction_Details")	
public class LoanSanction 
{
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name ="Sanction_Id")
	@Id
	private Integer sanctionId;
	
	@Column(name ="Sanction_Date")
	private LocalDate sanctionDate;
	
	@Column(name ="Applicant_Name")
	private String applicantName;
	
	@Column(name ="Contact_Number")
	private Long contactNo;
	
	@Column(name ="Loan_Sanction_Amount")
	private Double loanSanctionedAmount;
	
	@Column(name ="Rate_Of_Intereset")
	private Float rateOfInterest;
	
	@Column(name ="Loan_Tenure_Months")
	private Integer loanTenureInMonth;
	
	@Column(name ="Monthly_EMI_Amount")
	private Double monthlyEmiAmount;
	
	@Column(name ="Mode_Of_Payment")
	@Enumerated(EnumType.STRING)
	private ModeOfPaymentEnum modeOfPayment;
	
	@Column(name ="Loan_Sanction_Status")
	@Enumerated(EnumType.STRING)
	private LoanSanctionStatusEnum loanSanctionStatus;
	
	@Column(name ="Customer_Id")
	private Integer customerId;
	
	@Column(name ="Applicant_Id")
	private Integer appllicantId;
	
	@Column(name ="Cibil_Score")
	private Integer cibilScore;
	
	@Column(name ="Request_Loan_Amount")
	private Long requestedLoanAmount; 
	
	@Column(name ="Processing_Fees")
	private Double processingFees;
	
	@Column(name ="Processing_Fees_Status")
	private Double processingFeesStatus;
	
	@Column(name ="Emi_Start_date")
	private LocalDate emiStartDate;
	
	@Column(name ="Emi_End_date")
	private LocalDate emiEndDate;
	
	@Column(name ="Disbursment_Date")
	private LocalDate disbursmentDate;
}
