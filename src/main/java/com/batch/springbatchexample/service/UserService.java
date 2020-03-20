package com.batch.springbatchexample.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.batch.springbatchexample.model.User;
import com.batch.springbatchexample.repository.UserRepository;

@Service
public class UserService {
	
	@Autowired
	UserRepository userRepository;
	
	@Transactional
	public User getUserByName(String name) {
		return userRepository.findByName(name);
	}
	
	@Transactional
	public User getUserById(Integer id) {
		return userRepository.findById(id).get();
	}
	
	@Transactional
    public void saveUser(User user)
    {
        userRepository.save(user);
    }
    @Transactional
    public void updateUser(User user)
    {
        userRepository.save(user);
        
    }
    @Transactional
    public void deleteUser(int Id)
    {
        userRepository.deleteById(Id);
    }
    @Transactional
    public List<User> getAllUsers()
    {
       return userRepository.findAll();
    }

}
