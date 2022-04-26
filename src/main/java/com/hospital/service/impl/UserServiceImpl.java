package com.hospital.service.impl;

import java.util.ArrayList;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.hospital.model.User;
import com.hospital.repo.UserRepository;
import com.hospital.service.IUserService;

@Service
public class UserServiceImpl implements IUserService,UserDetailsService {
	@Autowired
	private UserRepository userRepo;
	@Autowired
	private BCryptPasswordEncoder pwdEncoder;

	@Override
	public void signUp(User user) {
		user.setPassword(pwdEncoder.encode(user.getPassword()));
		userRepo.save(user);
	}

	@Override
	public Optional<User> findByEmail(String email) {
		return userRepo.findByEmail(email);
	}
	
	@Override
	public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
		Optional<User> temp = findByEmail(email);
		if(temp.isEmpty()) {
			throw new UsernameNotFoundException(email+"does not exist");
		}
		User user = temp.get();
		ArrayList<String> roles = new ArrayList<String>(java.util.Arrays.asList("ADMIN"));
		return new org.springframework.security.core.userdetails.User(email, user.getPassword(),roles.stream().map(role->new SimpleGrantedAuthority(role)).collect(Collectors.toList()));
				
	}

}
