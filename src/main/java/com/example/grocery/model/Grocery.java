package com.example.grocery.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Grocery {
	private String name;
	
	private int unit;
	
	private int pricePerUnit;
	
	private String vendor;
	
	private Boolean rotten;
}
