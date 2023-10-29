package com.example.web.client;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.net.Socket;

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

                        dos.writeUTF("SEND_FILE");
                        System.out.print("Enter file path: ");
                        String filePath = br.readLine();
                        dos.writeUTF(filePath);

                        // gửi file cho server
                        fis = new FileInputStream(filePath);
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

                    } else if (choice == 2) {

                        dos.writeUTF("RECEIVE_FILE");
                        System.out.print("Enter file name to download: ");
                        String fileName = br.readLine();
                        dos.writeUTF(fileName);

                        System.out.print("Enter file path to save: ");
                        String filePath = br.readLine();
                        fos = new FileOutputStream(filePath + "//" + fileName);
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

                        fos.close();
                        System.out.println("File " + fileName + " received from server");

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

            // gửi file cho server
            fis = new FileInputStream(filePath);
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

    public String getListFile(String path) {
        try {
            dos.writeUTF("SHOW_LIST_FILE");
            dos.writeUTF(path);
            String listFile = dis.readUTF();
            return listFile;
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return null;
        }
    }
}
