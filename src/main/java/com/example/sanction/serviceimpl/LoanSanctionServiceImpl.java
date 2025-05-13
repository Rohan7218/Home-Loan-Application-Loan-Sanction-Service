package com.example.sanction.serviceimpl;

import org.modelmapper.ModelMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.sanction.config.LoanDisbursmentFeignApi;
import com.example.sanction.config.MailSanctionFeignApi;
import com.example.sanction.dto.LoanDisbursmentDTO;
import com.example.sanction.dto.LoanSanctionDTO;
import com.example.sanction.dto.LoanSanctionMailDTO;
import com.example.sanction.dto.LoanSanctionStatusEnum;
import com.example.sanction.dto.LoanSanctionStatusSTO;
import com.example.sanction.dto.ModeOfPaymentDTO;
import com.example.sanction.dto.ModeOfPaymentEnum;
import com.example.sanction.dto.ProcessingFeesEnum;
import com.example.sanction.dto.UpdateProcessingFeesDTO;
import com.example.sanction.entity.LoanSanction;
import com.example.sanction.exceptionHandling.CustomeException;
import com.example.sanction.pdfgeneration.LoanSanctionPdf;
import com.example.sanction.pdfgeneration.PdfGenerationService;
import com.example.sanction.repository.LoanSanctionRepository;
import com.example.sanction.service.LoanSanctionService;

@Service
public class LoanSanctionServiceImpl implements LoanSanctionService
{
	private static final Logger LOGGER=LoggerFactory.getLogger(LoanSanctionServiceImpl.class);
	
	@Autowired
	private LoanSanctionRepository loanSanctionRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PdfGenerationService pdfGenerationService;
	
	@Autowired
	private MailSanctionFeignApi mailSanctionFeignApi;
	
	@Autowired
	private LoanDisbursmentFeignApi loanDisbursmentFeignApi;
	
	@Override
	public String addSanctionDetails(LoanSanctionDTO loanSanctionDTO) 
	{
		LOGGER.info("LoanSanctionServiceImpl: addSanctionDetails : Entry");
		LoanSanction loanSanction = modelMapper.map(loanSanctionDTO, LoanSanction.class);
							 loanSanction.setLoanSanctionStatus(LoanSanctionStatusEnum.VERIFIED);
							 loanSanction.setProcessingFeesStatus(ProcessingFeesEnum.UNPAID);
							 
		loanSanctionRepository.save(loanSanction);
		LOGGER.info("LoanSanctionServiceImpl: addSanctionDetails : Exit");
		return "!!!...Loan Sanction details added SuccessFully...!!!";
	}
	
	@Override
	public String calculateEligibleLoanAmount(Integer sanctionId) 
	{
		LOGGER.info("LoanSanctionServiceImpl: calculateEligibleLoanAmount : Entry");
		if(loanSanctionRepository.findById(sanctionId).isPresent())
		{
			LOGGER.info("LoanSanctionServiceImpl: calculateEligibleLoanAmount : Entry");
			LoanSanction loanSanction = loanSanctionRepository.findById(sanctionId).get();
			
			Double eligibleLoanAmount=calculateLoanAmount(loanSanction.getNetMonthlyIncome(), loanSanction.getLoanTenureInMonth());
			
			loanSanction.setLoanSanctionedAmount(eligibleLoanAmount);
			
			loanSanctionRepository.save(loanSanction);
			
			LOGGER.info("LoanSanctionServiceImpl: calculateEligibleLoanAmount : Exit");
			return "Sanctioned Loan Amount :- "+eligibleLoanAmount;
		}
		else
		{
			LOGGER.info("LoanSanctionServiceImpl: calculateEligibleLoanAmount : Exit");
			throw new CustomeException("!!!...For Given Sanction Id Record Is Not Present...!!!");
		}
		
		
	}
	
	public static Double calculateLoanAmount(Double netMonthlyIncome, Integer loanTenureInMonth)
	{
		LOGGER.info("LoanSanctionServiceImpl: calculateLoanAmount : Entry");
        double monthlyInterestRate = 8.5 / 12 / 100;
        
        Double emi=netMonthlyIncome * 0.5;

        double ratePowerN = Math.pow(1 + monthlyInterestRate, loanTenureInMonth);

//        // Apply the rearranged formula:
//        // P = EMI * [(1 + R)^N – 1] / [R * (1 + R)^N]
        double loanAmount = emi * (ratePowerN - 1) / (monthlyInterestRate * ratePowerN);
       
        //To reduce decimal value.
        String  eligibleLoanAmount= String.format("%.2f", loanAmount);
        
        LOGGER.info("LoanSanctionServiceImpl: calculateLoanAmount : Exit");
        
        return Double.valueOf(eligibleLoanAmount);
	}

	@Override
	public byte[] getMonthlyEmi(Integer sanctionId) throws Exception
	{
		LOGGER.info("LoanSanctionServiceImpl: getMonthlyEmi : Entry");
		if(loanSanctionRepository.findById(sanctionId).isPresent())
		{
			LOGGER.info("LoanSanctionServiceImpl: getMonthlyEmi : Entry");
			LoanSanction loanSanction = loanSanctionRepository.findById(sanctionId).get();
			if(loanSanction.getCibilScore()>=750)
			{
				LOGGER.info("LoanSanctionServiceImpl: getMonthlyEmi : Entry");
				Float annualRate=7.99f;
				LOGGER.info("LoanSanctionServiceImpl: getMonthlyEmi : Exit");
				 double emi = calculateEMI(loanSanction.getLoanSanctionedAmount(), annualRate,loanSanction.getLoanTenureInMonth());
				 
				 String  calculatedEMI= String.format("%.2f", emi);
				 
				 
				 loanSanction.setMonthlyEmiAmount(Double.valueOf(calculatedEMI));
				 loanSanction.setRateOfInterest(annualRate);
				 loanSanction.setProcessingFees(loanSanction.getLoanSanctionedAmount()*0.005);
				 
				 loanSanctionRepository.save(loanSanction);    
				 LoanSanctionPdf loanSanctionPdf = modelMapper.map(loanSanction, LoanSanctionPdf.class);
				
				 byte[] generateSanctionLetter = pdfGenerationService.generateSanctionLetter(loanSanctionPdf);
				 				 
				 loanSanction.setGenerateSanctionLetter(generateSanctionLetter);
				 
				 loanSanctionRepository.save(loanSanction);
				 
				 LoanSanctionDTO loanSanctionDTO = modelMapper.map(loanSanction, LoanSanctionDTO.class);
				 
				
				 LoanSanctionMailDTO loanSanctionMailDTO=new LoanSanctionMailDTO();
				 									loanSanctionMailDTO.setTo(loanSanction.getEmailId());
				 									loanSanctionMailDTO.setSubject("Request for Approval of Sanctioned Loan Amount Unified Home Loan Pvt Ltd");
				 									loanSanctionMailDTO.setGenerateSanctionLetter(loanSanction.getGenerateSanctionLetter());
				 									loanSanctionMailDTO.setFileName("LoanSanctionApprovalMail.txt");
				 									loanSanctionMailDTO.setLoanSanctionDTO(loanSanctionDTO);
				 									loanSanctionMailDTO.setAttachmentName("SanctionLetter");
				
				try 
				{
				    mailSanctionFeignApi.loanSanctionMail(loanSanctionMailDTO);
				    LOGGER.info("Sanction email sent successfully to: " + loanSanction.getEmailId());
				} catch (Exception e) {
				    LOGGER.error("Failed to send sanction email to: " + loanSanction.getEmailId(), e);
				}
				  return generateSanctionLetter;
			}
			
			else
			{
				LOGGER.info("LoanSanctionServiceImpl: getMonthlyEmi : Entry");
				Float annualRate=8.50f;
				LOGGER.info("LoanSanctionServiceImpl: getMonthlyEmi : Exit");
				double emi = calculateEMI(loanSanction.getLoanSanctionedAmount(), annualRate,loanSanction.getLoanTenureInMonth());
				String  calculatedEMI= String.format("%.2f", emi);
				
				loanSanction.setMonthlyEmiAmount(Double.valueOf(calculatedEMI));
				loanSanction.setRateOfInterest(annualRate);
				loanSanction.setProcessingFees(loanSanction.getLoanSanctionedAmount()*0.005);
				loanSanctionRepository.save(loanSanction);
				 
				 LoanSanctionPdf loanSanctionPdf = modelMapper.map(loanSanction, LoanSanctionPdf.class);			 
				 
				byte[] generateSanctionLetter = pdfGenerationService.generateSanctionLetter(loanSanctionPdf);	
				
				loanSanction.setGenerateSanctionLetter(generateSanctionLetter);
				 loanSanctionRepository.save(loanSanction);
				 
				 LoanSanctionDTO loanSanctionDTO = modelMapper.map(loanSanction, LoanSanctionDTO.class);
				 
				 LoanSanctionMailDTO loanSanctionMailDTO=new LoanSanctionMailDTO();
				 									loanSanctionMailDTO.setTo(loanSanction.getEmailId());
				 									loanSanctionMailDTO.setSubject("Request for Approval of Sanctioned Loan Amount Unified Home Loan Pvt Ltd");
				 									loanSanctionMailDTO.setGenerateSanctionLetter(loanSanction.getGenerateSanctionLetter());
				 									loanSanctionMailDTO.setFileName("LoanSanctionApprovalMail.txt");
				 									loanSanctionMailDTO.setLoanSanctionDTO(loanSanctionDTO);
				
				 mailSanctionFeignApi.loanSanctionMail(loanSanctionMailDTO);
				  return generateSanctionLetter;
			}
		}
		else
		{
			LOGGER.info("LoanSanctionServiceImpl: getMonthlyEmi : Exit");
			throw new CustomeException("!!!...For Given Sanction Id Rercord Is Not Present...!!!!");
		}
	}


	public static double calculateEMI(double requestedLoanAmount, double annualRate, int loanTenureInMonth) 
	{
		LOGGER.info("LoanSanctionServiceImpl: calculateEMI : Entry");
	    double monthlyRate = annualRate / 12 / 100;
	    LOGGER.info("LoanSanctionServiceImpl: calculateEMI : Exit");
	    return (requestedLoanAmount * monthlyRate * Math.pow(1 + monthlyRate, loanTenureInMonth)) /
	           (Math.pow(1 + monthlyRate, loanTenureInMonth) - 1);
	}

	@Override
	public String updateLoanSanctionStatus(LoanSanctionStatusSTO loanSanctionStatusSTO, Integer sanctionId)
	{
		LOGGER.info("LoanSanctionServiceImpl: updateLoanSanctionStatus : Entry");
		
		if(loanSanctionRepository.findById(sanctionId).isPresent())
		{
			LoanSanction loanSanction = loanSanctionRepository.findById(sanctionId).get();
								 loanSanction.setLoanSanctionStatus(LoanSanctionStatusEnum.LOAN_SANCTIONED);
								 
			loanSanctionRepository.save(loanSanction);
			LOGGER.info("LoanSanctionServiceImpl: updateLoanSanctionStatus : Exit");
			
//			LoanSanctionPdf loanSanctionPdf = modelMapper.map(loanSanction, LoanSanctionPdf.class);
			
//			pdfGenerationService.generateSanctionLetter(loanSanctionPdf);
			
			return "Loan Sanction Status updated SuccessFully";
		}
		else
		{
			LOGGER.info("LoanSanctionServiceImpl: updateLoanSanctionStatus : Exit");
			throw new CustomeException("!!!...For Given Sanction Id Rercord Is Not Present...!!!!");
		}
	}
	
	
	@Override
	public String selectModeOfPayment(ModeOfPaymentDTO modeOfPaymentDTO, Integer sanctionId) 
	{
		if(loanSanctionRepository.findById(sanctionId).isPresent())
		{
			LoanSanction loanSanction = loanSanctionRepository.findById(sanctionId).get();
								 loanSanction.setModeOfPayment(modeOfPaymentDTO.getModeOfPayment());
			loanSanctionRepository.save(loanSanction);
			return "!!!...Mode Of Payment Set SuccessFully...!!!";
		}
		else
		{
			LOGGER.info("LoanSanctionServiceImpl: selectModeOfPayment : Exit");
			throw new CustomeException("!!!...For Given Sanction Id Rercord Is Not Present...!!!!");
		}
	}
	
	@Override
	public String updatePaymentStatus(Integer sanctionId, UpdateProcessingFeesDTO updateProcessingFeesDTO)
	{
		if(loanSanctionRepository.findById(sanctionId).isPresent())
		{
			LoanSanction loanSanction = loanSanctionRepository.findById(sanctionId).get();
								  loanSanction.setProcessingFeesStatus(updateProcessingFeesDTO.getProcessingFeesEnum());
			
		   loanSanctionRepository.save(loanSanction);
		   
		   if(loanSanction.getProcessingFeesStatus().equals(ProcessingFeesEnum.PAID))
		   {
			   LoanDisbursmentDTO loanDisbursmentDTO = modelMapper.map(loanSanction, LoanDisbursmentDTO.class);
			   loanDisbursmentFeignApi.addDisbursment(loanDisbursmentDTO);
		   }
		   LOGGER.info("LoanSanctionServiceImpl: updatePaymentStatus : Exit"); 
		   return "!!!...Payment Status Updated SuccessFully...!!!";
		}
		else
		{
			LOGGER.info("LoanSanctionServiceImpl: updatePaymentStatus : Exit");
			throw new CustomeException("!!!...For Given Sanction Id Rercord Is Not Present...!!!!");
		}
	}
	
}
