package io.egen.app.repository;

import java.util.List;

import io.egen.app.entity.User;

public interface UserRepository {

	List<User> findAll();

	User findOne(String userId);

	User findByEmail(String email);

	User create(User user);

	User update(User user);

	void delete(User existing);

}
