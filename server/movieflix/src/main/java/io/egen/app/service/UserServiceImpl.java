package io.egen.app.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import io.egen.app.entity.User;
import io.egen.app.exception.EntityAlreadyExistException;
import io.egen.app.exception.EntityNotFoundException;
import io.egen.app.repository.UserRepository;

@Service
public class UserServiceImpl implements UserService {

	@Autowired
	private UserRepository repository;

	@Override
	public List<User> findAll() {
		return repository.findAll();
	}

	@Override
	public User findOne(String userId) {
		User user = repository.findOne(userId);
		if (user == null) {
			throw new EntityNotFoundException("User not found");
		}
		return user;
	}

	@Transactional
	@Override
	public User create(User user) {
		User existing = repository.findByEmail(user.getEmail());
		if (existing != null) {
			throw new EntityAlreadyExistException("User already exists with this email");
		}
		return repository.create(user);
	}

	@Transactional
	@Override
	public User update(String userId, User user) {
		User existing = repository.findOne(userId);
		if (existing == null) {
			throw new EntityNotFoundException("User not found");
		}
		return repository.update(user);
	}

	@Transactional
	@Override
	public void remove(String userId) {
		User existing = repository.findOne(userId);
		if (existing == null) {
			throw new EntityNotFoundException("User not found");
		}
		repository.delete(existing);
	}

}
