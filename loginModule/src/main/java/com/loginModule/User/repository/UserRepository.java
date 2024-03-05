package com.loginModule.User.repository;

import org.springframework.data.jpa.repository.JpaRepository; 
import org.springframework.stereotype.Repository;

import com.loginModule.User.dto.User;

import java.util.Optional; 

@Repository
public interface UserRepository extends JpaRepository<User, Integer> { 
	Optional<User> findByName(String username); 
}
