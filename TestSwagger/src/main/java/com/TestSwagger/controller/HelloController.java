package com.TestSwagger.controller;

import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/TestSwagger")
@Api(tags="TestSwagger")
public class HelloController {

	@ApiOperation(value ="Say Hello by Swagger", response = String.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Successfully say Hello"),
		@ApiResponse(code = 401, message = "You are not authorized to get Hello"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@RequestMapping(method = RequestMethod.GET, produces="text/plain", consumes="application/json")
	public String getHello() {
		return "Swagger Hello World";
	}
	
	
	@ApiOperation(tags = "custom",value ="Say Hello By Swagger ID ", response = String.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Successfully say hello by id"),
		@ApiResponse(code = 401, message = "Yout are not authorized to get Hello By Id"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@RequestMapping(method = RequestMethod.GET, value="/{id}", produces = "text/plain", consumes ="application/json")
	public String getHelloById(@ApiParam(value="id", required=true) @PathVariable("id") int id) {
		return "Swagger by Id: "+id;
	}
	
	
	@ApiOperation(tags = "custom",value ="Post Hello to Swagger ", response = String.class)
	@ApiResponses({
		@ApiResponse(code = 200, message = "Successfully post hello to swagger"),
		@ApiResponse(code = 401, message = "Yout are not authorized to get post hello to swagger"),
		@ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
		@ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	})
	@RequestMapping(method = RequestMethod.POST, produces = "text/plain", consumes ="application/json")
	public String postHello(@ApiParam(value="msg", required=true) @RequestBody String msg) {
		return "Swagger says "+msg;
	}
}
