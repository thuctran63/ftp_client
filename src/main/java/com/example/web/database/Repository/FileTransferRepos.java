package com.example.web.database.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.web.database.entity.FileTransfer;

public interface FileTransferRepos extends JpaRepository<FileTransfer, Integer> {
    
}
