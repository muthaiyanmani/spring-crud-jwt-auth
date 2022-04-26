package com.hospital.service;

import java.util.Optional;

import com.hospital.model.User;

public interface IUserService {
	void signUp(User user);
	Optional<User> findByEmail(String email);
}
