package com.example.grocery.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.grocery.entity.UserDetailsEntity;

@Repository("userDetailsRepository")
public interface UserDetailsRepository extends JpaRepository<UserDetailsEntity, Integer>{

}
