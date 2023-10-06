package com.example.web.client;

import java.io.*;
import java.net.Socket;

public class Client {
    public Socket client = null;
    public DataInputStream dis = null;
    public DataOutputStream dos = null;
    public FileInputStream fis = null;
    public FileOutputStream fos = null;
    public BufferedReader br = null;
    public String inputFromUser = "";

    public static void main(String[] args) {
        Client c = new Client();
        c.doConnections();
    }

    public void doConnections() {
        try {
            InputStreamReader isr = new InputStreamReader(System.in);
            br = new BufferedReader(isr);
            System.out.println("Enter the IP Destination Address:");
            String ip = br.readLine(); 
            client = new Socket(ip, 3333);

        } catch (Exception e) {
            System.out.println("Unable to Connect to Server");
        }

        while (true) {
            try {
                dis = new DataInputStream(client.getInputStream());
                dos = new DataOutputStream(client.getOutputStream());
                System.out.println("Please Make a Choice : \n1.send file \n2receive file \nYour Choice: ");
                inputFromUser = br.readLine();
                int i = Integer.parseInt(inputFromUser);
                switch (i) {
                    case 1:
                        sendFile();
                        break;
                    case 2:
                        receiveFile();
                        break;
                    default:
                        System.out.println("Invalid Option !");
                }
            } catch (Exception e) {
                System.out.println("Some Error Occured!");
            }
        }
    }

    public void sendFile() {
        try {
            dos.writeUTF("TRANSFER_FILE");

            String filename = "", filedata = "";
            File file;
            byte[] data;
            System.out.println("Enter the filename: ");
            filename = br.readLine();
            file = new File(filename);

            System.out.println("Enter the destination ip: ");
            String ip = br.readLine();
            dos.writeUTF(ip);

            if (file.isFile()) {
                fis = new FileInputStream(file);
                data = new byte[fis.available()];
                fis.read(data);
                filedata = new String(data);

                String arr[] = filename.split("\\\\");
                System.out.println(arr[arr.length - 1]);

                dos.writeUTF(arr[arr.length - 1]);
                dos.writeUTF(filedata);
                System.out.println("File Send Successful!");

            } else {
                System.out.println("File Not Found!");
            }
        } catch (Exception e) {
            System.out.println("Some Error Occured!: " + e.getMessage());
        } finally {
            try {

            } catch (Exception e) {
            }
        }
    }

    public void receiveFile() {
        try {
            String filename = "", filedata = "";
            System.out.println("Enter the filename: ");
            filename = br.readLine();
            dos.writeUTF("DOWNLOAD_FILE");
            dos.writeUTF(filename);
            filedata = dis.readUTF();
            if (filedata.equals("")) {
                System.out.println("No Such File");
            } else {
                fos = new FileOutputStream(filename);
                fos.write(filedata.getBytes());
                fos.close();
            }
        } catch (Exception e) {
        }
    }
}
