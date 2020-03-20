package com.batch.springbatchexample.batch;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.batch.springbatchexample.model.User;

@Component
public class Processor implements ItemProcessor<User, User> {

	@Override
	public User process(User user) throws Exception {
		user.setSalary(user.getSalary()+100);
		return user;
	}

}
