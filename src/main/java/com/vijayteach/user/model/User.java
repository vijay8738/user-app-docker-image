package com.vijayteach.user.model;

import java.time.LocalDate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.PastOrPresent;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "user_table")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class User {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@NotBlank
	@Size(min = 3, max = 40, message = "name is required with atleast 3 characters")
	private String name;
	
	@PastOrPresent
	private LocalDate birthDate;
	@Email
	private String email;
	
	@Size(min = 3, max = 100, message = "name is required with atleast 3 characters")
	@NotBlank
	private String address;
}
