package com.bjs.webstore.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bjs.webstore.domain.UserWs;
import com.bjs.webstore.domain.repository.UserRepository;
import com.bjs.webstore.service.UserService;
@Service
public class UserServiceImpl implements UserService{
	@Autowired
	UserRepository userRepository;

	@Override
	public List<UserWs> getAllUsers() {
		return userRepository.getAllUsers();
	}
	
}
