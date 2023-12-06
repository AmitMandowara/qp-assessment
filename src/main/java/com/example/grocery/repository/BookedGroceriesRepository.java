package com.example.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.entity.BookedGroceriesEntity;

@Repository("bookedGroceriesRepository")
public interface BookedGroceriesRepository extends JpaRepository<BookedGroceriesEntity, Integer>{

}
