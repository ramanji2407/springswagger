package com.spring.pms.Entity;

import java.util.List;
import java.util.Set;
import java.util.stream.Stream;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.UniqueConstraint;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

@Table(name = "User_details",uniqueConstraints = {@UniqueConstraint(name="name_unique",columnNames ="User_name" )})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema( example = "1")
	private int id;
	
	@Schema( example = "ram244")
	@Column(name="User_name",unique = true)
	@NotEmpty(message = "Name_shoulde_not_be_empty")
	private String name;
	@Schema( example = "pasupuleti")
	@NotEmpty(message = "first_name_shoulde_not_be_empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "first_name should contain only alphabetic characters")
	private String first_name;
	@Schema( example = "ramanjaneyulu")
	@NotEmpty(message = "last_name_shoulde_not_be_empty")
    @Pattern(regexp = "^[a-zA-Z]+$", message = "last_name should contain only alphabetic characters")
	private String last_name;
	@Schema( example = "ram")
	@NotEmpty(message = "Pasword_shoulde_not_be_empty")
	private String password;
	@Schema(example = "pasupuletiramanji3@gmail.com")
	@Email(message = "please_enter_valid_email_adress ")
	@NotEmpty(message = "Email Shoulde not be empty")
	private String email;
	@Schema(example = "ROLE_MANAGER")
	@Pattern(regexp = "^(ROLE_MANAGER|ROLE_USER)$",message = "Role_shoulde_be_ROLE_MANAGER||ROLE_USER")
	private String role;
	
	@Schema(example = "Backend")
	@Pattern(regexp = "^(Backend|Frontend)$" ,message = "Department_should_be_either_Backend||Frontend")
	private String department; 
    @JsonIgnore
	@ManyToMany(mappedBy = "users",fetch = FetchType.LAZY)
	Set<Project>projects;

    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "user")
	private List<Task>tasks;
    
    @JsonIgnore
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "users")

 	private List<Subtask>subtasks;

	


}