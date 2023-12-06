package com.example.grocery.service;

import java.util.List;
import java.util.Optional;

import com.example.grocery.exception.InvalidOperationException;
import com.example.grocery.model.BookGrocery;
import com.example.grocery.model.Grocery;
import com.example.grocery.model.ManageGrocery;

public interface GroceryService {
	public void addGroceryItem(Grocery grocery);
	public List<Grocery> getAllGroceries();
	public void deleteGrocery(int id);
	public String updateGroceryItem(Grocery grocery, int id);
	public Optional<Grocery>  getGroceryItem(int id);
	public void manageGrocery(ManageGrocery manageGrocery) throws InvalidOperationException;
	public String bookGrocery(List<BookGrocery> bookGrocery, int userId);
}
