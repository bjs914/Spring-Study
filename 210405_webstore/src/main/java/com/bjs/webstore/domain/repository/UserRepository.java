package com.bjs.webstore.domain.repository;

import java.util.List;

import com.bjs.webstore.domain.User;

public interface UserRepository {
	List<User> getAllUsers();
}
