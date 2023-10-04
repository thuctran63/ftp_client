package com.example.server;

import java.net.ServerSocket;
import java.net.Socket;



public class FTPServer {
    private int port = 21;
    private String rootDir = System.getProperty("user.dir") + "\\" + "PublicFolder";
    private ServerSocket serverSocket = null;

    private static FTPServer instance;

    public static FTPServer getInstance() {
        if (instance == null) {
            instance = new FTPServer();
            SupportFunc.getInstance().createFolder(instance.rootDir);
        }
        return instance;
    }

    public void startServer() {
        try {
            serverSocket = new ServerSocket(port, port, null);
            System.out.println("Server FTP is running on port " + this.port + " ....");

            // chờ client kết nối tới server
            while (true) {
                Socket clientSocket = serverSocket.accept();
                new Thread(new ProcessClient(clientSocket,rootDir)).start();
                System.out.println("New client connected:" + clientSocket.getInetAddress().getHostAddress());
            }
            
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

}