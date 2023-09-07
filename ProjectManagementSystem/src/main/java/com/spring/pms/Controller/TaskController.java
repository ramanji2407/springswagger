package com.spring.pms.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.spring.pms.Entity.Task;
import com.spring.pms.Exceptions.DetailsNotFoundException;
import com.spring.pms.Response.ExceptionResponseMessage;
import com.spring.pms.Response.Response200;
import com.spring.pms.Response.Response201;
import com.spring.pms.Response.Response400;
import com.spring.pms.Response.Response401;
import com.spring.pms.Response.Response403;
import com.spring.pms.Response.Response404;
import com.spring.pms.Response.Response409;
import com.spring.pms.Response.Response500;
import com.spring.pms.Response.Taskapiresponse;
import com.spring.pms.Service.ApiRespons;
import com.spring.pms.Service.TaskService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;

@RequestMapping("/task")
@RestController
@SecurityRequirement(name="Bearer Authentication")

public class TaskController {
	@Autowired
	private TaskService taskService;
	@ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Taskapiresponse.class), mediaType = "application/json") },description = "Ok"),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class),mediaType = "application/json")},description = "Internal Server Error" )
       ,@ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       @ApiResponse(responseCode = "403", content = { @Content(examples = {
               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
               
             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),     
       @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Notfound" ),
 
       @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(),mediaType = "application/json")},description = "No Content" )
})  
	@Operation(summary = "Getting all the tasks ")

	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@GetMapping("/")
	public ResponseEntity< ApiRespons<List<Task>>>getAllTasks()
	{
		if(taskService.getAllTasks().isEmpty())
		{
			 throw new DetailsNotFoundException(ExceptionResponseMessage.TASK_DETAILS_NOT_FOUND.getMessage());

		}
		ApiRespons<List<Task>> response = new ApiRespons<>("Sucess", taskService.getAllTasks());

		 return new ResponseEntity<>(response,HttpStatus.OK) ;
	}
	@ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Taskapiresponse.class), mediaType = "application/json") },description = "Ok"),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class),mediaType = "application/json")},description = "Internal Server Error" ),
       @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       @ApiResponse(responseCode = "403", content = { @Content(examples = {
               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
               
             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),           
       
       
       @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Notfound" ),
       @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema())},description = "No Content" )
})
	@Operation(summary = "Getting the task with id ")

	@PreAuthorize("hasRole('ROLE_MANAGER')")

	@GetMapping("/{id}")
	public  ResponseEntity< ApiRespons<Task>> getAllTask(@PathVariable long id)
	{
		if(taskService.getAllTask(id)==null)
		{
			 throw new DetailsNotFoundException(ExceptionResponseMessage.TASK_DETAILS_NOT_FOUND.getMessage()+id);

		}
		ApiRespons<Task> response = new ApiRespons<>("Sucess",taskService.getAllTask(id));

		 return new ResponseEntity<>(response,HttpStatus.OK) ;
		
	}
	 @ApiResponses({
	        @ApiResponse(responseCode = "201", content = {
	            @Content(schema = @Schema(implementation = Response201.class), mediaType = "application/json") },description = "Created"),
	        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class,example = "{ \"status\": 500, \"message\": \"Database error\" }"),mediaType = "application/json")},description = "Internal Server Error" )
	       , @ApiResponse(responseCode = "400", content = { @Content(examples = {
	               @ExampleObject(name = "Useridempty",value = "{\"message\":\"User_id_should_not_be_empty\"}"),
	               @ExampleObject(name = "Useridtype",value = "{\"message\":\"User_id_should_be_integer_type\"}"),
	               @ExampleObject(name = "Projectidempty",value = "{\"message\":\"Project_id_should_not_be_empty\"}"),
	               @ExampleObject(name = "Projectidtype",value = "{\"message\":\"Project_id_should_be_integer_type\"}"),
	               @ExampleObject(name = "Tasknameempty",value = "{\"message\":\"Task_name_should_not_be_empty\"}"),
	               @ExampleObject(name = "Taskdescriptionempty",value = "{\"message\":\"Description_shoulde_not_be_empty\"}"),
	               @ExampleObject(name = "Taskduedatepattern",value = "{\"message\":\"Pattern_shoulde_be_MM/dd/yyyy\"}"),
	               @ExampleObject(name = "Taskstatuserror", value = "{\"message\":\"Should_be_only_Completed_or_InProgress\"}"),
	               @ExampleObject(name = "Taskcreateerror", value = "{\"message\":\"Inorder_to_create_task_project_shoulde_be_in_active\"}"),

	             },schema = @Schema(implementation = Response400.class),mediaType = "application/json")},description = "Bad Request" ),
	       @ApiResponse(responseCode = "404", content = { @Content(examples = {
	               @ExampleObject(name = "UseridNotfound",value = "{\"message\":\"User_id_Not_found\"}"),
	               @ExampleObject(name = "Projectidnotfound",value = "{\"message\":\"Project_id_Not_found\"}"),
	              
	             },schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Not_Found" ),
	       @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
	       @ApiResponse(responseCode = "403", content = { @Content(examples = {
	               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
	               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
	               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
	               
	             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),     

	})

	
		@PreAuthorize("hasRole('ROLE_MANAGER')")
		@Operation(summary = "Posting the task with userid and projectid")

	@PostMapping("/user/{id}/project/{pid}")
	public ResponseEntity<ApiRespons<String>> postTask(@Valid @RequestBody Task task, @PathVariable int id,@PathVariable int pid)
	{
		
		taskService.postTask(task, id,pid);
        ApiRespons<String> response = new ApiRespons<>("Sucess", "Sucessfully_Created_Task");
		return new ResponseEntity<>(response,HttpStatus.CREATED);


	}
	@ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation =Taskapiresponse.class ), mediaType = "application/json") },description = "Ok"),
        @ApiResponse(responseCode = "500", content = { @Content(examples = {@ExampleObject(name="DatbaseConnection",value = "{\"message\":\"Check_UserName_And_Password_of_DataBase\"}")}, schema = @Schema(implementation = Response500.class,example = "{ \"status\": 500, \"message\": \"Database error\" }"),mediaType = "application/json")},description = "Internal Server Error" )
       , @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = Response400.class),mediaType = "application/json")},description = "Bad Request" ),
       @ApiResponse(responseCode = "400", content = { @Content(examples = {
               @ExampleObject(name = "Taskidempty",value = "{\"message\":\"Task_id_should_not_be_empty\"}"),
               @ExampleObject(name = "Tasknameempty",value = "{\"message\":\"Task_name_should_not_be_empty\"}"),
               @ExampleObject(name = "Taskdescriptionempty",value = "{\"message\":\"Description_shoulde_not_be_empty\"}"),
               @ExampleObject(name = "Taskduedatepattern",value = "{\"message\":\"Pattern_shoulde_be_MM/dd/yyyy\"}"),
               @ExampleObject(name = "Taskstatuserror", value = "{\"message\":\"Should_be_only_Completed_or_InProgress\"}"),
               @ExampleObject(name = "Taskstatuserror", value = "{\"message\":\"Task_status_should_be_inprogress_if_subtasks_inprogress\"}"),

             },schema = @Schema(implementation = Response400.class),mediaType = "application/json")},description = "Bad Request" ),
       @ApiResponse(responseCode = "404", content = { @Content(examples = {
               @ExampleObject(name = "TaskidNotfound",value = "{\"message\":\"Task_id_Not_found\"}"),
              
             },schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Not_Found" ), @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       @ApiResponse(responseCode = "403", content = { @Content(examples = {
               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
               
             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),            


})
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@Operation(summary = "Updating the task details")

	@PutMapping("/{id}")
	public  ResponseEntity< ApiRespons<Task>> updateTask(@Valid @RequestBody Task task ,  @PathVariable long id)
	{
		if(taskService.getAllTask(id)==null)
		{
			 throw new DetailsNotFoundException(ExceptionResponseMessage.TASK_DETAILS_NOT_FOUND.getMessage()+"_to_update");

		}
        ApiRespons<Task> response = new ApiRespons<>("Sucess",taskService.updateTask(task, id));

		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	 @ApiResponses({
	        @ApiResponse(responseCode = "200", content = {
	            @Content(schema = @Schema(implementation = Response200.class), mediaType = "application/json") },description = "Ok"),
	        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class,example = "{ \"status\": 500, \"message\": \"Database error\" }"),mediaType = "application/json")},description = "Internal Server Error" ),
	       @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
	       @ApiResponse(responseCode = "403", content = { @Content(examples = {
	               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
	               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
	               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
	               
	             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),     	      
	       
	       
	       @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Notfound" )

	})
		@PreAuthorize("hasRole('ROLE_MANAGER')")
		@Operation(summary = "Deleting the task details")

	@DeleteMapping("/{id}")
	public  ResponseEntity<ApiRespons<String>>  deleteTask( @PathVariable long id)
	{
		if(taskService.getAllTask(id)==null)
		{
			 throw new DetailsNotFoundException(ExceptionResponseMessage.TASK_DETAILS_NOT_FOUND.getMessage()+"_to_delete");

		}
		taskService.deleteTask(id);

        ApiRespons<String> response = new ApiRespons<>("Sucess","Deleted_Sucessfully");

		return new ResponseEntity<>(response,HttpStatus.OK);


	}
	

}
