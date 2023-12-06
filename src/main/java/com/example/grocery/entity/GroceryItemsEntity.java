package com.example.grocery.entity;



import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "grocery_items")
@Getter
@Setter
public class GroceryItemsEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name = "name")
	private String name;
	
	@Column(name = "unit")
	private int unit;
	
	@Column(name = "priceperunit")
	private int pricePerUnit;
	
	@Column(name = "vendor")
	private String vendor;
	
	@Column(name = "is_rotten")
	private Boolean rotten;
}
