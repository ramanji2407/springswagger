package com.spring.pms.Entity;

import java.util.Date;
import java.util.List;
import java.util.Set;
import springfox.documentation.annotations.ApiIgnore;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import io.swagger.v3.oas.annotations.media.Schema;
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
import lombok.Data;
import lombok.NoArgsConstructor;
import springfox.documentation.annotations.ApiIgnore;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
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
	@JsonIgnore
	 @ManyToMany(fetch = FetchType.LAZY,cascade = CascadeType.ALL)
	 Set<User>user;
	
	@OneToMany(cascade = CascadeType.ALL,fetch = FetchType.LAZY,orphanRemoval = true)
	@JoinColumn(name = "project_id")
	private List<Task>tasks;
	
	
	
}