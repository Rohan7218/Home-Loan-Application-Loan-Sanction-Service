package com.example.sanction.serviceimpl;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sanction.dto.LoanSanctionDTO;
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
		}
		return null;
	}
	
	
	
	
	
	
	
	
	public static double calculateEMI(double requestedLoanAmount, double annualRate, int loanTenureInMonth) {
	    double monthlyRate = annualRate / 12 / 100;
	    return (requestedLoanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanTenureInMonth)) /
	           (Math.pow(1 + monthlyRate, loanTenureInMonth) - 1);
	}

	
	
}
