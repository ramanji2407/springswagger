package com.spring.pms.Response;

import io.swagger.v3.oas.annotations.media.Schema;

public class Response500 {
	//@Schema( example = "500")
	//private int statuscode;
	@Schema(example = "Cannot_get_the_information_due_to_internal_server_error")
	private String message;
//	public int getstatuscode() {
//		return statuscode;
//	}
//	public void setStatuscode(int statuscode) {
//		this.statuscode = statuscode;
//	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	

}
