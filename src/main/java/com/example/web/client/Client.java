package com.example.web.client;

import java.util.ArrayList;
import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.util.List;

public class Client {

    private Socket clientSocket = null;
    private String ipHost = null;
    private int port = 0;

    private BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private DataInputStream dis = null;
    private DataOutputStream dos = null;
    private FileInputStream fis = null;
    private FileOutputStream fos = null;

    public Client(String ipHost, int port, String username, String password) {
        this.ipHost = ipHost;
        this.port = port;
    }

    public boolean connect() {
        try {

            clientSocket = new Socket(ipHost, port);
            dis = new DataInputStream(clientSocket.getInputStream());
            dos = new DataOutputStream(clientSocket.getOutputStream());
            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public void doTask() {
        try {
            if (connect()) {
                while (true) {

                    int choice = Integer.parseInt(br.readLine());

                    if (choice == 1) {

                    } else if (choice == 2) {

                    } else if (choice == 5) {
                        System.out.println("Exit");
                        break;
                    } else if (choice == 3) {
                        dos.writeUTF("SET_CURRENT_FOLDER");
                        System.out.print("Enter folder path: ");
                        String folderPath = br.readLine();
                        dos.writeUTF(folderPath);
                    } else if (choice == 4) {
                        dos.writeUTF("SHOW_LIST_FILE");
                        String listFile = dis.readUTF();
                        System.out.println(listFile);
                    }

                    else {
                        System.out.println("Invalid choice");
                    }
                }
            }

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    public boolean sendFile(String filePath, String pathSave) {
        try {
            dos.writeUTF("SEND_FILE");
            dos.writeUTF(filePath);
            dos.writeUTF(pathSave);

            String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
            // gửi file cho server
            fis = new FileInputStream(System.getProperty("user.dir") + "\\"+ fileName);
            byte[] buffer = new byte[4096];
            int read = 0;

            while (true) {
                if ((read = fis.read(buffer)) > 0) {
                    dos.writeUTF("SENDED");
                    dos.flush();
                    dos.write(buffer, 0, read);

                    if (!dis.readUTF().equals("ACK")) {
                        dos.writeUTF("DONE");
                        break;
                    }
                } else {
                    dos.writeUTF("DONE");
                    break;
                }
            }

            fis.close();
            System.out.println("File " + filePath + " sent to server");
            return true;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }

    public List<String> getListFile(String path) {
        try {
            dos.writeUTF("SHOW_LIST_FILE");
            dos.writeUTF(path);
            String listFile = dis.readUTF();
            String listDirectory = dis.readUTF();

            List<String> resultList = new ArrayList<>();
            resultList.add(listFile);
            resultList.add(listDirectory);

            return resultList;

        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }

    public boolean recivedFile(String filePath) {
        try {
            dos.writeUTF("RECEIVE_FILE");
            dos.writeUTF(filePath);
            String fileName = filePath.substring(filePath.lastIndexOf("\\") + 1, filePath.length());
            fos = new FileOutputStream("E:\\test\\" + fileName);
            byte[] buffer = new byte[4096];
            int read = 0;
            dos.writeUTF("READY_TO_SEND");
            while (true) {
                if (dis.readUTF().equals("SENDED")) {

                    read = dis.read(buffer);
                    dos.writeUTF("ACK");
                    fos.write(buffer, 0, read);
                } else {
                    break;
                }
            }
            System.out.println("File " + filePath + " received from server");
            fos.close();
            return true;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        return false;
    }

}
