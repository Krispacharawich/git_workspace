package com.TestSwagger.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.TestSwagger.Exception.NotFoundException;
import com.TestSwagger.Model.Pet;
import com.TestSwagger.data.PetData;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponses;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.Authorization;
import io.swagger.annotations.AuthorizationScope;

@Api(value = "/pet", authorizations = {
		@Authorization(value ="petstore_auth",
			scopes = {
					@AuthorizationScope(scope = "write:pets", description ="modify pets in your account"),
					@AuthorizationScope(scope = "read:pets", description ="read your pets")
			})
})
@RestController
public class PetResources extends AbstractResource{

	private static PetData data = new PetData();
	
	@ApiOperation(notes = "Returns a pet when 0 < ID <= 10. ID > 10 or non-integers will simulate API error conditions", value ="Find pet by ID", nickname = "getPetById",
			tags = {"Pets"})
	@ApiResponses({
		@ApiResponse(code = 200, message = "Nice!", response = Pet.class),
		@ApiResponse(code = 400, message = "Invalid ID supplied", response = ApiResponse.class),
		@ApiResponse(code = 404, message = "Pet not found", response = ApiResponse.class)
	})
	@RequestMapping(value = "/pets/{id}", method = RequestMethod.GET, produces = "application/json")
	public ResponseEntity<Pet> getPetById(@ApiParam(value = "ID of pet that needs to be fetched", allowableValues = "range[1,10]", required = true) 
			@PathVariable("id") Integer petId) throws Exception {
			Pet pet = data.getPetById(petId);
			if (pet != null) {
				return ResponseEntity.ok().body(pet);
			}else {
				throw new NotFoundException(com.TestSwagger.Model.ApiResponse.ERROR, "Pet "+petId+ "not found");
			}
		}
		
}
