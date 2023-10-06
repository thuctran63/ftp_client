package com.example.web.server;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ProcessClient implements Runnable {
    private Socket clientSocket;
    private String ipAdress = null;
    private String rootDirServer = null;

    public ProcessClient(Socket clientSocket, String rootDir) {
        this.clientSocket = clientSocket;
        this.ipAdress = clientSocket.getInetAddress().getHostAddress();
        this.rootDirServer = rootDir;

    }

    @Override
    public void run() {
        try {
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            while (true) {
                String command = dis.readUTF();
                if (command.equals("FILE_SEND_FROM_CLIENT")) {
                    System.out.println("Client " + ipAdress + " is sending file to server");
                    receiveFile(clientSocket);
                } else if (command.equals("DOWNLOAD_FILE")) {
                    downloadFile(clientSocket);
                }
            }
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void receiveFile(Socket clientSocket) {

        try {
            String fileData = null;
            String fileName = null;
            String ipAdress = clientSocket.getInetAddress().getHostAddress();
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            FileOutputStream fos = null;

            fileName = dis.readUTF();
            fileData = dis.readUTF();
            String folderPath = rootDirServer + "\\" + ipAdress;
            SupportFunc.getInstance().createFolder(folderPath);

            fos = new FileOutputStream(folderPath + "\\" + fileName);
            fos.write(fileData.getBytes());
            fos.close();
            System.out.println("File " + fileName + " received from client " + ipAdress);
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private void downloadFile(Socket clientSocket) {
        try {
            
            String fileName = null;
            String ipAdress = clientSocket.getInetAddress().getHostAddress();
            DataInputStream dis = new DataInputStream(clientSocket.getInputStream());
            DataOutputStream dos = new DataOutputStream(clientSocket.getOutputStream());
            FileInputStream fis = null;

            fileName = dis.readUTF();

            File file = new File(rootDirServer + "\\" + ipAdress + "\\" + fileName);
            if (file.exists()) {
                fis = new FileInputStream(file);
                byte[] buffer = new byte[4096];
                int read = 0;
                while ((read = fis.read(buffer)) > 0) {
                    dos.write(buffer, 0, read);
                }
                fis.close();
            } else {
                dos.writeUTF("File not found");
            }
        } catch (Exception e) {

        }
    }
}
