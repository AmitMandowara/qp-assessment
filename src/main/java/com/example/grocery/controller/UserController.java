package com.example.grocery.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.grocery.model.BookGrocery;
import com.example.grocery.model.Grocery;
import com.example.grocery.model.Response;
import com.example.grocery.service.GroceryService;

@RestController
@RequestMapping("/user/grocery")
@CrossOrigin
public class UserController {

	@Autowired
	private GroceryService groceryService;
	
	@GetMapping(value = "", produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<List<Grocery>>> getAllGroceries(){
		try {
			return new ResponseEntity<>(new Response<List<Grocery>>(200, groceryService.getAllGroceries()), HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
	
	@PostMapping(value = "/book", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Response<String>> bookGroceries(@RequestBody List<BookGrocery> bookGroceryList, @RequestParam("userId") int userId){
		try {
			return new ResponseEntity<>(new Response<String>(200, groceryService.bookGrocery(bookGroceryList, userId)), HttpStatus.OK);
		}catch(Exception e) {
			throw new ResponseStatusException(HttpStatus.INTERNAL_SERVER_ERROR, e.getMessage(), e);
		}
	}
}
