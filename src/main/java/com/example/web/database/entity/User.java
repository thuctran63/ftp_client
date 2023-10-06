package com.example.web.database.entity;

import jakarta.persistence.*;
@Entity

@Table(name = "user")
public class User {
    @Id
    @Column(name = "IdUser")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int IdUser;

    @Column(name = "user_name")
    private String UserName;

    @Column(name = "ip_address")
    private String IpAdress;

    @Column(name = "file_path")
    private String rootDir;

    //getter and setter
    public int getIdUser() {
        return IdUser;
    }

    public String getUserName() {
        return UserName;
    }

    public String getIpAdress() {
        return IpAdress;
    }

    public String getRootDir() {
        return rootDir;
    }

    public void setIdUser(int idUser) {
        this.IdUser = idUser;
    }

    public void setUserName(String userName) {
        this.UserName = userName;
    }

    public void setIpAdress(String ipAdress) {
        this.IpAdress = ipAdress;
    }

    public void setRootDir(String rootDir) {
        this.rootDir = rootDir;
    }

    //constructor with param
    public User(int idUser, String userName, String ipAdress, String rootDir) {
        this.IdUser = idUser;
        this.UserName = userName;
        this.IpAdress = ipAdress;
        this.rootDir = rootDir;
    }


}
