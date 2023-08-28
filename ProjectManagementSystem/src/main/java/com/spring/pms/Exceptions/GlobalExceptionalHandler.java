package com.spring.pms.Exceptions;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ProblemDetail;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.security.access.AccessDeniedException;


import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.security.SignatureException;


@RestControllerAdvice
public class GlobalExceptionalHandler {
	
	@ExceptionHandler(Exception.class)
    public ProblemDetail handleSecurityException(Exception ex) {
        ProblemDetail errorDetail = null;
        System.out.println(9);

        if (ex instanceof BadCredentialsException) {
            System.out.println(9);

            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(401), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Invalid_Username_Or_Password");
        }

        if (ex instanceof AccessDeniedException) {
            System.out.println(19);

            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "Your_Not_Authorized!");

        }

        if (ex instanceof SignatureException) {
             System.out.println(119);

            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "JWT Signature not valid");
        }
     //   System.out.println(191);

        if (ex instanceof ExpiredJwtException) {
            errorDetail = ProblemDetail
                    .forStatusAndDetail(HttpStatusCode.valueOf(403), ex.getMessage());
            errorDetail.setProperty("access_denied_reason", "JWT Token already expired !");
        }

        return errorDetail;
    }
	@ExceptionHandler(DetailsNotFoundException.class)
	 public ResponseEntity<?> handleStudentNotFoundRxception(DetailsNotFoundException detailsNotFoundException)
	 {            System.out.println(9);

		DetailNotfound notfound=new DetailNotfound();
		notfound.setMessage(detailsNotFoundException.getMessage());
		notfound.setTimestamp(new Date());
		notfound.setStatus(HttpStatus.NOT_FOUND);
		return new ResponseEntity<>(notfound,HttpStatus.NOT_FOUND);
	 }
	@ExceptionHandler(UserAlreadyExistException.class)
	 public ResponseEntity<?> handleUserAlreadyExist(UserAlreadyExistException alreadyExist)
	 {            System.out.println(9);

		UserAlreadexist notfound=new UserAlreadexist();
		notfound.setMessage(alreadyExist.getMessage());
		notfound.setTimestamp(new Date());
		notfound.setStatus(HttpStatus.CONFLICT);
		return new ResponseEntity<>(notfound,HttpStatus.CONFLICT);
	 }
	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<Map<String, String>>handleMethodArgumentNotValidException(MethodArgumentNotValidException ex)
	{            System.out.println(9);

	Map<String, String>response=new HashMap<String, String>();	
	ex.getBindingResult().getAllErrors().forEach(
			(error)->{
				String fieldname=((FieldError)error).getField();
				String message=error.getDefaultMessage();
				response.put(fieldname, message);
			});
		return new  ResponseEntity<Map<String,String>>(response,HttpStatus.BAD_REQUEST);
	}
	@ExceptionHandler(BadRequestException.class)
	 public ResponseEntity<?> handleBadrequeste(BadRequestException alreadyExist)
	 {            System.out.println(9);

		BadRequest notfound=new BadRequest();
		notfound.setMessage(alreadyExist.getMessage());
		notfound.setTimestamp(new Date());
		notfound.setStatus(HttpStatus.BAD_REQUEST);
		return new ResponseEntity<>(notfound,HttpStatus.BAD_REQUEST);
	 }
	

}
