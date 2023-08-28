package com.spring.pms.Apiresponse;

import com.spring.pms.Response.Projectapiresponse;
import com.spring.pms.Response.Response401;
import com.spring.pms.Response.Response403;
import com.spring.pms.Response.Response404;
import com.spring.pms.Response.Response500;

import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@ApiResponses({
    @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = Projectapiresponse.class), mediaType = "application/json") },description = "Ok"),
    @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = Response500.class),mediaType = "application/json")},description = "Internal Server Error" )
   ,@ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = Response401.class),mediaType = "application/json")},description = "Unauthorized" ),
   @ApiResponse(responseCode = "404", content = { @Content(schema = @Schema(implementation = Response404.class),mediaType = "application/json")},description = "Notfound" ),
   @ApiResponse(responseCode = "403", content = { @Content(examples = {
           @ExampleObject(name = "Authorization_Error",value = "{\"message\":\"Your_Not_Authorized\"}"),
           @ExampleObject(name = "JWT_Signature_Error",value = "{\"message\":\"JWT_Signature_not_valid\"}"),
           @ExampleObject(name = "JWT_Token_expired !",value = "{\"message\":\"JWT_Token_already_expired_!\"}"),
           
         },schema = @Schema(implementation = Response403.class),mediaType = "application/json")},description = "Forbidden" ),
   @ApiResponse(responseCode = "204", content = { @Content(schema = @Schema(),mediaType = "application/json")},description = "No Content" )
}) 
public class Getallprojectapiresponse {

}
