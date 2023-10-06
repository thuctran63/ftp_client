package com.example.web.database.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.database.entity.History;

public interface HistoryRepos extends JpaRepository<History, Integer> {
    
}
