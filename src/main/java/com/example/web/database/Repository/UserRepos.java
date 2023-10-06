package com.example.web.database.Repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.web.database.entity.User;

@Repository
public interface UserRepos extends JpaRepository<User, Integer>{

}
