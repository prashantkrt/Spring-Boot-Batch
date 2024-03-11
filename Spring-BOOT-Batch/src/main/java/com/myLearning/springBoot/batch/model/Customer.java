package com.myLearning.springBoot.batch.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Table(name = "Customer")
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString
@Builder
public class Customer {

	@Id
	private Integer id;

	private String firstName;

	private String lastName;

	private String email;

	private String gender;

	private String contactNo;

	private String country;

	private String dob;

}
