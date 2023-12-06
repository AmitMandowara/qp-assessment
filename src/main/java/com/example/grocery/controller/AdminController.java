package com.example.grocery.controller;

import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.grocery.exception.InvalidOperationException;
import com.example.grocery.model.Grocery;
import com.example.grocery.model.ManageGrocery;
import com.example.grocery.model.Response;
import com.example.grocery.service.GroceryService;

@RestController
@RequestMapping("/admin/grocery")
@CrossOrigin
public class AdminController {
	
	@Autowired
	private GroceryService groceryService;
	
	@PostMapping(value = "", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> add(@RequestBody Grocery grocery){
		try {
			if(Optional.ofNullable(grocery).isEmpty() || StringUtils.isBlank(grocery.getName())) {
				return new ResponseEntity<>(new Response<String>(400, null, "Request or name in request can't be empty"), HttpStatus.BAD_REQUEST);
			}
			groceryService.addGroceryItem(grocery);
			return new ResponseEntity<>(new Response<String>(200, "Grocery Item record created successfully"), HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<List<Grocery>>> getAllGroceries(){
		try {
			return new ResponseEntity<>(new Response<List<Grocery>>(200, groceryService.getAllGroceries()), HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@DeleteMapping(value = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> deleteGrocery(@PathVariable("id") int id){
		try {
			groceryService.deleteGrocery(id);
			return new ResponseEntity<>(new Response<String>(200, "Record deleted successfully." ), HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@PutMapping(value = "/{id}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> updateGroceryItem(@PathVariable("id") int id, @RequestBody Grocery grocery){
		try {
			if(Optional.ofNullable(grocery).isEmpty() || StringUtils.isBlank(grocery.getName())) {
				return new ResponseEntity<>(new Response<String>(400, null, "Request or name in request can't be empty"), HttpStatus.BAD_REQUEST);
			}
			return new ResponseEntity<>(new Response<String>(200, groceryService.updateGroceryItem(grocery, id)), HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@GetMapping(value = "/item/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<Grocery>> getGroceryItemById(@PathVariable("id") int id){
		try {
			Optional<Grocery> groceryOp = groceryService.getGroceryItem(id);
			if(groceryOp.isEmpty()) {
				return new ResponseEntity<>(new Response<Grocery>(200, null, "No grocery item found for this id"), HttpStatus.OK);
			}
			return new ResponseEntity<>(new Response<Grocery>(200, groceryOp.get()), HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@PostMapping(value = "/manage", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> manageGrocery(@RequestBody ManageGrocery manageGrocery){
		try {
			groceryService.manageGrocery(manageGrocery);
			return new ResponseEntity<>(new Response<String>(200, "Grocery Item added/removed successfully"), HttpStatus.OK);
		} catch(InvalidOperationException e) {
			return new ResponseEntity<>(new Response<String>(400, null, "Grocery items removed are more than gorcery items present"), HttpStatus.BAD_REQUEST);
		} catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
}
