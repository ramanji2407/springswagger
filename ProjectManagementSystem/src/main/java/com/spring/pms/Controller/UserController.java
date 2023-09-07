package com.spring.pms.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.spring.pms.Dto.AcessTokenResponseDto;
import com.spring.pms.Dto.AcessTokenResponseDto.AcessTokenResponseDtoBuilder;
import com.spring.pms.Dto.RefreshTokenRequest;
import com.spring.pms.Dto.Request;
import com.spring.pms.Dto.UserDto;
import com.spring.pms.Entity.Project;
import com.spring.pms.Entity.RefreshToken;
import com.spring.pms.Entity.User;
import com.spring.pms.Exceptions.DetailsNotFoundException;
import com.spring.pms.Response.ExceptionResponseMessage;
import com.spring.pms.Response.Response201;
import com.spring.pms.Response.Response400;
import com.spring.pms.Response.Response401;
import com.spring.pms.Response.Response403;
import com.spring.pms.Response.Response404;
import com.spring.pms.Response.Response409;
import com.spring.pms.Response.Response500;
import com.spring.pms.Response.ResponseMessage;
import com.spring.pms.Response.Userapiresponse;
import com.spring.pms.Service.ApiRespons;
import com.spring.pms.Service.Jwtservice;
import com.spring.pms.Service.ProjectService;
import com.spring.pms.Service.RefreshTokenService;
import com.spring.pms.Service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;

@RestController
@RequestMapping("/user")
public class UserController {
	@Autowired
private	UserService userService;
	@Autowired
	private Jwtservice jwtservice;
	@Autowired
	private AuthenticationManager authmanager;
	@Autowired 
	private ProjectService projectService;
	@Autowired
	private RefreshTokenService refreshTokenService;
	@ApiResponses({
        @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Userapiresponse.class), mediaType = "application/json") },description = "Ok"),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class),mediaType = "application/json")},description = "Internal Server Error" )
        , @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Notfound" ),
        @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       @ApiResponse(responseCode = "403", content = { @Content(examples = {
               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
               
             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),       
       @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(),mediaType = "application/json")},description = "No Content" )


})  	
	@Operation(summary = "Getting all the users   ")

	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@SecurityRequirement(name="Bearer Authentication")

	@GetMapping("/")
	public ResponseEntity< ApiRespons<List<UserDto>>> getAllUsers()
	{
		List<UserDto> list1=userService.getAllUsers();
		if(list1.isEmpty())
		{
			 throw new DetailsNotFoundException(ExceptionResponseMessage.USER_DETAILS_NOT_FOUND.getMessage());

		}
        ApiRespons<List<UserDto>> response = new ApiRespons<>("Sucess", list1);

		 return new ResponseEntity<>(response,HttpStatus.OK) ;
			
	}
	@ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation = Userapiresponse.class), mediaType = "application/json") },description = "Ok"),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class),mediaType = "application/json")},description = "Internal Server Error" ),
       @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       @ApiResponse(responseCode = "403", content = { @Content(examples = {
               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
               
             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),              @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Notfound" ),
       @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema())},description = "No Content" )
})
	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@Operation(summary = "Getting  the user with id   ")
	@SecurityRequirement(name="Bearer Authentication")


	@GetMapping("/{id}")
	public ResponseEntity< ApiRespons<UserDto>> getAllUser(@PathVariable int id)
	{UserDto user=userService.getAllUser(id);
	if(user==null)
	{
		throw new DetailsNotFoundException(ExceptionResponseMessage.USER_DETAILS_NOT_FOUND.getMessage()+id);

	}
    ApiRespons<UserDto> response = new ApiRespons<>("Sucess", user);

	 return new ResponseEntity<>(response,HttpStatus.OK) ;
	}
	
	@ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = Response201.class), mediaType = "application/json") },description = "Created"),
        @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class,example = "{ \"status\": 500, \"message\": \"Database error\" }"),mediaType = "application/json")},description = "Internal Server Error" )
       , @ApiResponse(responseCode = "400", content = { @Content(examples = {
               @ExampleObject(name = "Idempty",value = "{\"message\":\"User_id_should_not_be_empty\"}"),
               @ExampleObject(name = "Idtype",value = "{\"message\":\"User_id_should_be_integer_type\"}"),
               @ExampleObject(name = "Nameempty",value = "{\"message\":\"Name_should_not_be_empty\"}"),
               @ExampleObject(name = "Paswordempty",value = "{\"message\":\"Pasword_shoulde_not_be_empty\"}"),
               @ExampleObject(name = "EmailPattern",value = "{\"message\":\"Pattern_shoulde_be_An_email_address_consists_of_a_username,_an_@_sign,_and_a_domain_name\"}"),
               @ExampleObject(name = "Roletype",value = "{\"message\":\"Role_shoulde_be_either_user_admin\"}"),
               @ExampleObject(name = "Departmenttype",value = "{\"message\":\"Department_should_be_either_backend_frontend\"}"),
               @ExampleObject(name = "Nameconflict",value = "{\"message\":\"Name_should_be_unique\"}"),

             },schema = @Schema(implementation = Response400.class),mediaType = "application/json")},description = "Bad Request" ),

       @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = Response409.class),mediaType = "application/json")},description = "Conflicts" ),
       @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       

})
	@Operation(summary = "Posting  the user details  ")


	@PostMapping("/")
	public ResponseEntity<ApiRespons<String>> postUser(@Valid @RequestBody User user)
	{
		
		
		userService.postUser(user);
        ApiRespons<String> response = new ApiRespons<>("Sucess", "Sucessfully_Created_User");

		return new ResponseEntity<>(response,HttpStatus.CREATED);
	}
	
		@ApiResponses({
        @ApiResponse(responseCode = "200", content = {
            @Content(schema = @Schema(implementation =Userapiresponse.class ), mediaType = "application/json") },description = "Ok"),
        @ApiResponse(responseCode = "500", content = { @Content(examples = {@ExampleObject(name="DatbaseConnection",value = "{\"message\":\"Check_UserName_And_Password_of_DataBase\"}")}, schema = @Schema(implementation = Response500.class,example = "{ \"status\": 500, \"message\": \"Database error\" }"),mediaType = "application/json")},description = "Internal Server Error" )
        ,@ApiResponse(responseCode = "400", content = { @Content(examples = {
               @ExampleObject(name = "Idempty",value = "{\"message\":\"Id_should_not_be_empty\"}"),
               @ExampleObject(name = "Idtype",value = "{\"message\":\"Id_should_be_integer_type\"}"),
               @ExampleObject(name = "Nameempty",value = "{\"message\":\"Name_should_not_be_empty\"}"),
               @ExampleObject(name = "Paswordempty",value = "{\"message\":\"Pasword_shoulde_not_be_empty\"}"),
               @ExampleObject(name = "EmailPattern",value = "{\"message\":\"Pattern_shoulde_be_An_email_address_consists_of_a_username,_an_@_sign,_and_a_domain_name\"}"),
               @ExampleObject(name = "Emailempty",value = "{\"message\":\"Email_shoulde_not_be_empty\"}"),

               @ExampleObject(name = "Roletype",value = "{\"message\":\"Role_shoulde_be_either_user_admin\"}"),
               @ExampleObject(name = "Departmenttype",value = "{\"message\":\"Department_should_be_either_backend_frontend\"}"),
             },schema = @Schema(implementation = Response400.class),mediaType = "application/json")},description = "Bad Request" ),
        @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Notfound" ),
       @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       @ApiResponse(responseCode = "403", content = { @Content(examples = {
               @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
               @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
               @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
               
             },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),              


})
	@Operation(summary = "Updating  the user details  ")
		@SecurityRequirement(name="Bearer Authentication")

	@PreAuthorize("hasRole('ROLE_MANAGER')")
	@PutMapping("/{id}")
	public ResponseEntity<ApiRespons<User>>  updateUser(@Valid @RequestBody User user ,  @PathVariable int id)
	{
		UserDto user1=userService.getAllUser(id);
		if(user1==null)
		{
			throw new DetailsNotFoundException(ExceptionResponseMessage.USER_DETAILS_NOT_FOUND.getMessage()+id+"to_update");

		}
	    ApiRespons<User> response = new ApiRespons<>("Sucess", userService.updateUser(user, id));


		return new ResponseEntity<>( response,HttpStatus.OK);
	}
	 	
	@ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = AcessTokenResponseDto.class), mediaType = "application/json") },description = "Created"),
        @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
       

})
	@Operation(summary = "Generating token with the username and passsword ")

	@PostMapping("/token")
	@ResponseBody
	//public ResponseEntity<ResponseMessage> generateToken(@RequestBody Request request) {
	public AcessTokenResponseDto generateToken(@RequestBody Request request) {



		 Authentication authentication = authmanager.authenticate(
          new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
		 if (authentication.isAuthenticated()) {
			 RefreshToken createRefreshToken = refreshTokenService.createRefreshToken(request.getUsername());
	             String generateToken = jwtservice.generateToken(request.getUsername());
	             return AcessTokenResponseDto.builder().jwtToken(generateToken).refreshToken(createRefreshToken.getToken()).build();

	        } else {
	            throw new UsernameNotFoundException("invalid user request !");
	        }

	}
	@ApiResponses({
        @ApiResponse(responseCode = "201", content = {
            @Content(schema = @Schema(implementation = AcessTokenResponseDto.class), mediaType = "application/json") },description = "Created"),
        @ApiResponse(responseCode = "404", content = { @Content(examples = {
                @ExampleObject(name = "RefreshToken_NotFound",value = "{\"message\":\"Refresh_Token_Not_Found\"}"),
                
                
              },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "NotFound" ),              

})
	@Operation(summary = "Genearting Jwt token with the help refresh token ")
	 @PostMapping("/refreshToken")
	    public AcessTokenResponseDto refreshToken(@RequestBody RefreshTokenRequest refreshTokenRequest) {
		 
//		 return refreshTokenService.findByToken(refreshTokenRequest.getToken())
//	                .map(refreshTokenService::verifyExpiration)
//	                .map(RefreshToken::getUser)
//	                .map(userInfo -> {
//	                    String accessToken = jwtservice.generateToken(userInfo.getName());
//	                    return AcessTokenResponseDto.builder().jwtToken(accessToken).refreshToken(refreshTokenRequest.getToken()).build();
//	                            
//	                }).orElseThrow(() -> new RuntimeException(
//	                        "Refresh token is not in database!"));
			//System.out.println(refreshTokenRequest.getToken());

		 RefreshToken findByToken = refreshTokenService.findByToken(refreshTokenRequest.getToken()).get();
		 
		//System.out.println(findByToken.getToken());
		 refreshTokenService.verifyExpiration(findByToken);
		 String name = findByToken.getUser().getName();
		 String accessToken = jwtservice.generateToken(name);
		 AcessTokenResponseDto dto= new AcessTokenResponseDto();
		 dto.setJwtToken(accessToken);
		 dto.setRefreshToken(refreshTokenRequest.getToken());
		return dto;
	    }
	    

	
}

