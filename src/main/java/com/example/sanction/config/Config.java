package com.example.sanction.config;

import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class Config 
{
	@Bean
    public OpenAPI enquiryOpenAPI() 
	{
        return new OpenAPI()
                .info(new Info()
                        .title("Service API")
                        .version("1.0")
                        .description("API documentation")
                        .contact(new Contact()
                                .name("Home Loan")
                                .email("sontakkerohan751@gmail.com")
                                .url("https://xyz/linkedin.com")
                        )
                );
    }
	
	@Bean
	public ModelMapper modelMapper() 
	{
		ModelMapper modelMapper = new ModelMapper();
		modelMapper.getConfiguration().setMatchingStrategy(MatchingStrategies.STRICT);
		return modelMapper;
	}
}
