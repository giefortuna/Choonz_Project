package com.qa.choonz.service;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Service;

import com.qa.choonz.exception.UserNotFoundException;
import com.qa.choonz.persistence.domain.User;
import com.qa.choonz.persistence.repository.UserRepository;
import com.qa.choonz.rest.dto.UserDTO;

@Service
public class UserService {

	// variables

	private UserRepository repo;
	private ModelMapper mapper;

	// constructor

	public UserService(UserRepository repo, ModelMapper mapper) {
		super();
		this.repo = repo;
		this.mapper = mapper;
	}

	// methods

	private UserDTO mapToDTO(User user) {
		return this.mapper.map(user, UserDTO.class);
	}

	public UserDTO create(User user) {
		User created = this.repo.save(user);
		return this.mapToDTO(created);
	}

	public List<UserDTO> read() {
		return this.repo.findAll().stream().map(this::mapToDTO).collect(Collectors.toList());
	}

	public UserDTO read(long id) {
		User user = this.repo.findById(id).orElseThrow(UserNotFoundException::new);
		return this.mapToDTO(user);
	}

	public UserDTO update(User user, long id) {
		User toUpdate = this.repo.findById(id).orElseThrow(UserNotFoundException::new);
		toUpdate.setUsername(user.getUsername());
		toUpdate.setPassword(user.getPassword());
		User updatedUser = this.repo.save(toUpdate);
		return this.mapToDTO(updatedUser);
	}

	public boolean delete(long id) {
		this.repo.deleteById(id);
		return !this.repo.existsById(id);
	}

	// figuring out/playing with
	public Long login(String username, String password) {
		return null;
	}

}
