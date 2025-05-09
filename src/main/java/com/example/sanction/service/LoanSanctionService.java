package com.example.sanction.service;

import com.example.sanction.dto.LoanSanctionDTO;
import com.example.sanction.dto.LoanSanctionStatusSTO;

public interface LoanSanctionService
{

	String addSanctionDetails(LoanSanctionDTO loanSanctionDTO);


	byte[] getMonthlyEmi(Integer sanctionId) throws Exception;

	String calculateEligibleLoanAmount(Integer sanctionId);

	String updateLoanSanctionStatus(LoanSanctionStatusSTO loanSanctionStatusSTO, Integer sanctionId);

}
