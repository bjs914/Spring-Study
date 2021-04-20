package com.bjs.webstore.domain.repository;

import java.util.List;

import com.bjs.webstore.domain.UserWs;

public interface UserRepository {
	List<UserWs> getAllUsers();
}
