package com.nazarukiv.springwebblog.repo;

import com.nazarukiv.springwebblog.models.Post;
import org.springframework.data.repository.CrudRepository;

public interface PostRepository extends CrudRepository<Post, Long> { }
