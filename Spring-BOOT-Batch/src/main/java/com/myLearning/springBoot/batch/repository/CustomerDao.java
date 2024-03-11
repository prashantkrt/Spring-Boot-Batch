package com.myLearning.springBoot.batch.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.myLearning.springBoot.batch.model.Customer;

public interface CustomerDao extends JpaRepository<Customer, Integer>{

}
