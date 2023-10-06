package com.example.web.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "history")
public class History {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "IdHistory")
    private int idHistory;

    @ManyToOne
    @JoinColumn(name = "IdUser")
    private User IdUser;

    @Column(name = "IdUserSend")
    private String IdUserSend;
    @Column(name = "fileName")
    private String fileName;

    //getter and setter

    public int getIdHistory() {
        return idHistory;
    }

    public User getIdUser() {
        return IdUser;
    }

    public String getIdUserSend() {
        return IdUserSend;
    }

    public String getFileName() {
        return fileName;
    }

    public void setIdHistory(int idHistory) {
        this.idHistory = idHistory;
    }

    public void setIdUser(User IdUser) {
        this.IdUser = IdUser;
    }

    public void setIdUserSend(String IdUserSend) {
        this.IdUserSend = IdUserSend;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    
}
