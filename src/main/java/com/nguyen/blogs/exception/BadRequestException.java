package com.nguyen.blogs.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;
import com.nguyen.blogs.Response.ApiResponse;

/*
 * Yêu cầu bị thiếu thông tin bắt buộc, như các tham số hoặc trường bắt buộc trong một biểu mẫu.
 * Yêu cầu có thông tin không hợp lệ, chẳng hạn như kiểu dữ liệu không đúng hoặc giá trị không hợp lệ.
 * Yêu cầu không được xác thực hoặc không có quyền truy cập tới nguồn tài nguyên được yêu cầu.
 * */

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class BadRequestException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	private ApiResponse apiResponse;
	
	public BadRequestException(ApiResponse apiResponse) {
		super();
		this.apiResponse = apiResponse;
	}

	public BadRequestException(String message) {
		super(message);
	}

	public BadRequestException(String message, Throwable cause) {
		super(message, cause);
	}

	public ApiResponse getApiResponse() {
		return apiResponse;
	}
}