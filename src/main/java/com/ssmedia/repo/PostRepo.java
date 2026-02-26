package com.ssmedia.repo;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ssmedia.model.Post;

public interface PostRepo extends JpaRepository<Post, Long> {

	public List<Post> findByUserId(Integer id);

}
