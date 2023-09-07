package com.spring.pms.Entity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
//@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")

public class Subtask {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Schema( example = "1")
    private Long id;
 //   @Pattern(regexp = "^[a-zA-Z]+$", message = "Subtask_name should contain only alphabetic characters")

	@NotEmpty(message = "Subtask_name_shoulde_not_be_empty")
	@Schema( example = "Wireframe Design")
    private String name;
	@Schema( example = "Create wireframes   Design")
	@NotEmpty(message = "Description_shoulde_not_be_empty")
    private String description;
	@JsonFormat(shape =JsonFormat.Shape.STRING,pattern = "MM/dd/yyyy")
	@Schema(example = "08/08/2022")
    private Date due_date;
	@Pattern(regexp = "^(Completed|InProgress)$",message = "must match Completed|InProgress ")
	@Schema(example = "Completed")
	private String status;
   @JsonIgnore
    @ManyToOne
    private Task task;
  @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "assigned_user_id") 

    private User users;


  
}