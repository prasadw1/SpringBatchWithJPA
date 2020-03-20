package com.batch.springbatchexample.batch;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.batch.springbatchexample.model.User;
import com.batch.springbatchexample.repository.UserRepository;

@Component
public class BatchWriter implements ItemWriter<User> {

	@Autowired
	UserRepository userRepository;
	
	@Override
	public void write(List<? extends User> users) throws Exception {
		System.out.println("USers" + users);
		userRepository.saveAll(users);
		
	}

}
