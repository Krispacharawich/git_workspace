package com.TestSwagger.controller;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;

import com.TestSwagger.Exception.NotFoundException;
import com.TestSwagger.Model.ApiResponse;
import org.springframework.http.HttpStatus;
public class AbstractResource {
	
	@CrossOrigin(allowedHeaders ="foo", origins ="*")
	@ResponseStatus(HttpStatus.NOT_FOUND)
	@ExceptionHandler(NotFoundException.class)
	public ApiResponse exception(NotFoundException e) {
		return new ApiResponse(ApiResponse.ERROR, e.getMessage());
	}

}
