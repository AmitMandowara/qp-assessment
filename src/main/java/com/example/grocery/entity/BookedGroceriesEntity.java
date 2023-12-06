package com.example.grocery.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Getter;
import lombok.Setter;

@Entity(name = "booked_groceries")
@Getter
@Setter
public class BookedGroceriesEntity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
	@JoinColumn(name = "grocery_id",referencedColumnName = "id")
	private GroceryItemsEntity groceryId;
	
	@ManyToOne
	@JoinColumn(name = "booked_by_user_id", referencedColumnName = "id")
	private UserDetailsEntity bookedByUserId;
	
	@Column(name = "units_booked")
	private int unitsBooked;
	
	@Column(name = "total_price")
	private Long totalPrice;
	
}
  