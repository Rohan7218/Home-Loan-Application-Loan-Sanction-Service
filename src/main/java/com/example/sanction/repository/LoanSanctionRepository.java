package com.example.sanction.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.sanction.entity.LoanSanction;

@Repository
public interface LoanSanctionRepository extends JpaRepository<LoanSanction, Integer>
{
	
}
