package com.spring.pms.Entity;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Task {
		@Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
		@Schema( example = "1")
	    private Long id;
		
		@Schema( example = " Design UI")
		@NotEmpty(message = "Task_name_shoulde_not_be_empty")
	 //   @Pattern(regexp = "^[a-zA-Z]+$", message = "Task name should contain only alphabetic characters")

	    private String name;
		
	    //@Pattern(regexp = "^[a-zA-Z]+$", message = "Task name should contain only alphabetic characters")
		@Schema( example = "Create UI mockups  ")
		@NotEmpty(message = "Description_shoulde_not_be_empty")
	    
	    private String description;
		
		@Schema(example = "08/08/2022")
		@JsonFormat(shape =JsonFormat.Shape.STRING,pattern = "MM/dd/yyyy")
	    private Date due_date;
		
		@Schema(example = "Completed")
		@Pattern(regexp = "^(Completed|InProgress)$")
		private String status;
	

		@ManyToOne(fetch = FetchType.LAZY)
		@JoinColumn(name="project_id")
		private Project project;
	

		@ManyToOne()
		@JoinColumn(name="user_id")

		private User user;
	

		@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true,mappedBy = "task")

		List<Subtask>subtask;

}