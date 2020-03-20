package com.batch.springbatchexample.controller;

import java.util.List;

import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.batch.springbatchexample.model.User;
import com.batch.springbatchexample.repository.UserRepository;
import com.batch.springbatchexample.service.UserService;

@RestController
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	JobLauncher jobLauncher;
	
	@Autowired
	Job job;
	
	@Autowired
	UserService userService;
	
	@GetMapping("csvToDb")
	public BatchStatus getUsers() {
		try {
			JobExecution execution = jobLauncher.run(job, new JobParameters());
			return execution.getStatus();
		} catch (JobExecutionAlreadyRunningException | JobRestartException | JobInstanceAlreadyCompleteException
				| JobParametersInvalidException e) {
			e.printStackTrace();
		}
		
		return null;
	}
	
	@GetMapping
    public List<User> getAllUsers()
    {
       return userService.getAllUsers();
    }
	
	@GetMapping("name/{name}")
	public User getUserByName(@PathVariable String name) {
		return userService.getUserByName(name);
	}
	
	@GetMapping("/{id}")
	public User getUserById(@PathVariable Integer id) {
		return userService.getUserById(id);
	}
	
	@PostMapping
    public void saveUser(@RequestBody User user)
    {
		userService.saveUser(user);
    }
	
	@PutMapping
    public void updateUser(@RequestBody User user)
    {
		userService.updateUser(user);
    }
	
    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id)
    {
    	userService.deleteUser(id);
    }

}
