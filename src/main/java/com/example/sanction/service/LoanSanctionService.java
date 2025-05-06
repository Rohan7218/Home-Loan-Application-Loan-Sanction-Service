package com.example.sanction.service;

import com.example.sanction.dto.LoanSanctionDTO;

public interface LoanSanctionService
{

	String addSanctionDetails(LoanSanctionDTO loanSanctionDTO);


	Object getMonthlyEmi(Integer sanctionId);

	String calculateEligibleLoanAmount(Integer sanctionId);


}
