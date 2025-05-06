package com.example.sanction.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sanction.dto.LoanSanctionDTO;
import com.example.sanction.dto.LoanSanctionStatusEnum;
import com.example.sanction.entity.LoanSanction;
import com.example.sanction.repository.LoanSanctionRepository;
import com.example.sanction.service.LoanSanctionService;

@Service
public class LoanSanctionServiceImpl implements LoanSanctionService
{
	@Autowired
	private LoanSanctionRepository loanSanctionRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Override
	public String addSanctionDetails(LoanSanctionDTO loanSanctionDTO) 
	{
		LoanSanction loanSanction = modelMapper.map(loanSanctionDTO, LoanSanction.class);
							 loanSanction.setLoanSanctionStatus(LoanSanctionStatusEnum.VERIFIED);
							 
		loanSanctionRepository.save(loanSanction);
		return "!!!...Loan Sanction details added SuccessFully...!!!";
	}
	

	
	@Override
	public Object getMonthlyEmi(Integer sanctionId) {
		if(loanSanctionRepository.findById(sanctionId).isPresent())
		{
			
			LoanSanction loanSanction = loanSanctionRepository.findById(sanctionId).get();
			if(loanSanction.getCibilScore()>=750)
			{
				double annualRate=7.99;
			return 	calculateEMI(loanSanction.getRequestedLoanAmount(), annualRate,loanSanction.getLoanTenureInMonth());
			}
			
			else
			{
				double annualRate=8.50;
				return 	calculateEMI(loanSanction.getRequestedLoanAmount(), annualRate, loanSanction.getLoanTenureInMonth());
			}

	@Override
	public String calculateEligibleLoanAmount(Integer sanctionId) 
	{
		if(loanSanctionRepository.findById(sanctionId).isPresent())
		{
			LoanSanction loanSanction = loanSanctionRepository.findById(sanctionId).get();
			
			Double eligibleLoanAmount=calculateLoanAmount(loanSanction.getNetMonthlyIncome(), loanSanction.getLoanTenureInMonth());
			
			loanSanction.setLoanSanctionedAmount(eligibleLoanAmount);
			loanSanctionRepository.save(loanSanction);
			return "Sanctioned Loan Amount :- "+eligibleLoanAmount;
			

		}
		return null;
	}
	

	public static double calculateEMI(double requestedLoanAmount, double annualRate, int loanTenureInMonth) {
	    double monthlyRate = annualRate / 12 / 100;
	    return (requestedLoanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanTenureInMonth)) /
	           (Math.pow(1 + monthlyRate, loanTenureInMonth) - 1);
	}

	
	

	public static Double calculateLoanAmount(Double netMonthlyIncome, Integer loanTenureInMonth)
	{
//		// Convert annual interest rate to monthly (as a decimal)
        double monthlyInterestRate = 8.5 / 12 / 100;
        
        Double emi=netMonthlyIncome * 0.5;
//
//        // Calculate (1 + R)^N
        double ratePowerN = Math.pow(1 + monthlyInterestRate, loanTenureInMonth);
//
//        // Apply the rearranged formula:
//        // P = EMI * [(1 + R)^N – 1] / [R * (1 + R)^N]
        double loanAmount = emi * (ratePowerN - 1) / (monthlyInterestRate * ratePowerN);
        return loanAmount;
	}

}
