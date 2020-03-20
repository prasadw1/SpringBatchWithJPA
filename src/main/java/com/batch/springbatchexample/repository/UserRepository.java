package com.batch.springbatchexample.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.batch.springbatchexample.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	User findByName(String name);
	
	void deleteById(Integer Id);
}
