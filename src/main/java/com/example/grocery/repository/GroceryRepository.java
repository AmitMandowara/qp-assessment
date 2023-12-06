package com.example.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.entity.GroceryItemsEntity;
@Repository("groceryRepository")
public interface GroceryRepository extends JpaRepository<GroceryItemsEntity, Integer>{
	
}
