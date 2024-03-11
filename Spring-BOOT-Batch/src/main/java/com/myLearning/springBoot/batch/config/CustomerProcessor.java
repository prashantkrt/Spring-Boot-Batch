package com.myLearning.springBoot.batch.config;

import org.springframework.batch.item.ItemProcessor;

import com.myLearning.springBoot.batch.model.Customer;

//ItemProcessor<Customer, Customer> reads the customer and writes the customer
public class CustomerProcessor implements ItemProcessor<Customer, Customer> {

	@Override
	public Customer process(Customer item) throws Exception {
		System.out.println("Processing");
		return item;
	}

//	public Customer process(Customer item)
//	{
//		//write some logic for filtering 
////		if(item.getCountry().equals("India"))		
////		System.out.println("Processing");
//		System.out.println("Processing");
//		return item;
//		
//	}

}
