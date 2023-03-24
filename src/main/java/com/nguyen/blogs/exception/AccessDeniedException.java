package com.nguyen.blogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.nguyen.blogs.Response.ApiResponse;
/*
 * cố gắng truy cập vào một tài nguyên hoặc chức năng mà họ không được cấp quyền hoặc không thuộc về nhóm người dùng được phép truy cập vào tài nguyên
 * */



@ResponseStatus(code = HttpStatus.UNAUTHORIZED)
public class AccessDeniedException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	private ApiResponse apiResponse;

	private String message;

	public AccessDeniedException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public AccessDeniedException(String message) {
		super(message);
		this.message = message;
	}

	public AccessDeniedException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}

	public void setApiResponse(ApiResponse apiResponse) {
		this.apiResponse = apiResponse;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}


}
