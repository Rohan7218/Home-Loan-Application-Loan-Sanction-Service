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
}
