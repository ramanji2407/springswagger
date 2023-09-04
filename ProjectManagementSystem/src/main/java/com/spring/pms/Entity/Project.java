package com.spring.pms.Entity;

import java.util.Date;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Project {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema( example = "1")

	private int id;
	@NotEmpty(message = "Project_name_shoulde_not_be_empty")
	@Column(name="Project_name")
   // @Pattern(regexp = "^[a-zA-Z]+$", message = "Task name should contain only alphabetic characters")
	@Schema( example = "KitchenManagementSystem")
	private String name;
	@NotEmpty(message = "Description_shoulde_not_be_empty")
   // @Pattern(regexp = "^[a-zA-Z]+$", message = "Task name should contain only alphabetic characters")
	@Schema( example = "Develop Website ")
	private String description;
	@JsonFormat(shape =JsonFormat.Shape.STRING,pattern = "MM/dd/yyyy")
	@Schema(example = "08/08/2022")
	private Date startDate;
	@Schema(example = "08/08/2022")
	@JsonFormat(shape =JsonFormat.Shape.STRING,pattern = "MM/dd/yyyy")
	private Date deadlineDate;
	@Pattern(regexp = "^(Active|Inactive)$")
    @Schema(type = "string", allowableValues = { "Active", "Inactive" }, example = "Active")
	private String status;
	   

	   @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	    @JoinTable(name = "Project_User_TABLE",
	            joinColumns = {
	                    @JoinColumn(name = "Project_id", referencedColumnName = "id")
	            },
	            inverseJoinColumns = {
	                    @JoinColumn(name = "User_id", referencedColumnName = "id")
	            }
	    )
	  // @Fetch(FetchMode.JOIN)
	 Set<User>users;
	   
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,mappedBy = "project")
	@JsonBackReference List<Task>tasks;
	
	
	
}