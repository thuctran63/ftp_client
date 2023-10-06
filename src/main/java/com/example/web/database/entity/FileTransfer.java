package com.example.web.database.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "filetransfer")
public class FileTransfer {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "Id")
    private int Id;

    @Column(name = "FileName")
    private String fileName;

    @Column(name = "IpHost")
    private String IpHost;

    @Column(name = "IpDestination")
    private String IpDestination;

    @Column(name = "isSend")
    private int isSend;

    //setter and getter
    public int getId() {
        return Id;
    }

    public String getFileName() {
        return fileName;
    }   

    public String getIpHost() {
        return IpHost;
    }

    public String getIpDestination() {
        return IpDestination;
    }

    public int getCheck() {
        return isSend;
    }

    public void setId(int Id) {
        this.Id = Id;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setIpHost(String IpHost) {
        this.IpHost = IpHost;
    }

    public void setIpDestination(String IpDestination) {
        this.IpDestination = IpDestination;
    }

    public void setCheck(int Check) {
        this.isSend = Check;
    }
    
    //constructor with param
    public FileTransfer(String fileName, String IpHost, String IpDestination) {
        this.fileName = fileName;
        this.IpHost = IpHost;
        this.IpDestination = IpDestination;
        this.isSend = 0;
    }
}
