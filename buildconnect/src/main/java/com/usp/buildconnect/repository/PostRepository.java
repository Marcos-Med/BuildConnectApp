package com.usp.buildconnect.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.usp.buildconnect.entity.Post;

@Repository
public interface PostRepository extends JpaRepository<Post, Long>{	
}
