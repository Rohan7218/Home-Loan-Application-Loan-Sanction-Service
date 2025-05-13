package com.example.sanction.service;

import com.example.sanction.dto.LoanSanctionDTO;
import com.example.sanction.dto.LoanSanctionStatusSTO;
import com.example.sanction.dto.ModeOfPaymentDTO;
import com.example.sanction.dto.UpdateProcessingFeesDTO;

public interface LoanSanctionService
{

	String addSanctionDetails(LoanSanctionDTO loanSanctionDTO);


	byte[] getMonthlyEmi(Integer sanctionId) throws Exception;

	String calculateEligibleLoanAmount(Integer sanctionId);

	String updateLoanSanctionStatus(LoanSanctionStatusSTO loanSanctionStatusSTO, Integer sanctionId);

	String selectModeOfPayment(ModeOfPaymentDTO modeOfPaymentDTO, Integer sanctionId);

	String updatePaymentStatus(Integer sanctionId, UpdateProcessingFeesDTO updateProcessingFeesDTO);


	

}
