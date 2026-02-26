package com.ssmedia.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.ssmedia.model.User;

public interface UserRepo extends JpaRepository<User, Long> {

	List<User> findByUsernameAndPassword(String username, String password);

	@Query(value = "SELECT distinct username FROM users", nativeQuery = true)
	List<String> findAllUsernames();
	
	@Query(value = "SELECT * from User WHERE username = ?1 and password = ?2", nativeQuery = true)
	User findByUserAndPass(String username, String password);

}
