package com.myLearning.springBoot.batch.config;

import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.transform.DelimitedLineTokenizer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;
import com.myLearning.springBoot.batch.model.Customer;
import com.myLearning.springBoot.batch.repository.CustomerDao;

@Configuration
public class SpringBatchConfig {

	@Autowired
	private CustomerDao repo;

	@Autowired
	private JobRepository jobRepository;

	@Autowired
	private PlatformTransactionManager transactionManager;

	// Item reader
	@Bean
	public FlatFileItemReader<Customer> customerReader() {
		// to read the data from the csv file
		FlatFileItemReader<Customer> itemReader = new FlatFileItemReader<>();
		itemReader.setResource(new FileSystemResource("/Users/prashantkumartiwary/Desktop/customers.csv"));
		itemReader.setName("csv-reader");

		// how many lines we wanted to skip
		// wanted to read from second line as first line contains header and attribute
		// name
		itemReader.setLinesToSkip(1);
		itemReader.setLineMapper(lineMapper());

		return itemReader;
	}

	// setting whether we have to read the empty data or not
	
	private LineMapper<Customer> lineMapper() {

		DefaultLineMapper<Customer> lineMapper = new DefaultLineMapper<>();

		DelimitedLineTokenizer lineTokenizer = new DelimitedLineTokenizer();

		// telling whatever you are reading this is how you have to read csv file
	
		lineTokenizer.setDelimiter(",");// set it to empty
		lineTokenizer.setStrict(false);// I want to add such data also which are empty
		// how we have read it setting it below line
		lineTokenizer.setNames("id", "firstName", "lastName", "email","gender", "contactNo", "country", "dob");

		// mapping with our pojo
		BeanWrapperFieldSetMapper<Customer> fieldSetMapper = new BeanWrapperFieldSetMapper<>();
		fieldSetMapper.setTargetType(Customer.class);

		lineMapper.setLineTokenizer(lineTokenizer);
		lineMapper.setFieldSetMapper(fieldSetMapper);

		return lineMapper;
	}

	// item processor
	@Bean
	public CustomerProcessor customerProcessor() {
		return new CustomerProcessor();
	}

	// item writer
	@Bean
	public RepositoryItemWriter<Customer> customerWriter() {
		RepositoryItemWriter<Customer> itemWriter = new RepositoryItemWriter<>();
		itemWriter.setRepository(repo);
		itemWriter.setMethodName("save");
		return itemWriter;
	}

	// step
	@Bean
	public Step step() {
		return new StepBuilder("step-1", jobRepository)
				.<Customer, Customer>chunk(10, transactionManager)
				.reader(customerReader())
				.processor(customerProcessor())
				.writer(customerWriter())
				.build();

	}

	// job
	@Bean
	public Job job() {
		//return new JobBuilder("customers-import", jobRepository).start(step()).next(step2()).next(step3()).build();
		return new JobBuilder("customers-import", jobRepository)
				.start(step())
				.build();
	}

	// job Launcher
	//In rest controller

}
