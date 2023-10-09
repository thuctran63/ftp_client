package com.example.web.client;

import java.io.BufferedReader;
import java.io.FileReader;
import java.security.Key;
import java.util.HashMap;
import java.util.Map;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class Security {

    private static final String AES_ALGORITHM = "AES";
    private static Security instance;
    Key key = new SecretKeySpec("00112233445566778899AABBCCDDEEFF".getBytes(), "AES");
    public static Security getInstance() {
        if (instance == null) {
            instance = new Security();
        }
        return instance;
    }


    public boolean vertify_user(String username, String password) {

        try {
            String filePath = System.getProperty("user.dir") + "\\" + "src\\main\\user.txt";
            BufferedReader reader = new BufferedReader(new FileReader(filePath));
            String line;
            Map<String, String> usernamePasswordMap = new HashMap<>();

            while ((line = reader.readLine()) != null) {
                // Tách dòng thành username và password
                String[] parts = line.split(" ");
                if (parts.length == 2) {
                    usernamePasswordMap.put(parts[0], parts[1]);
                }
            }
            reader.close();

            if (usernamePasswordMap.containsKey(username) && usernamePasswordMap.get(username).equals(password)) {
                return true;
            }
            return false;

        } catch (Exception e) {
            return false;
        }

    }

    public byte[] encryptData(String data) throws Exception {
        Cipher cipher = Cipher.getInstance(AES_ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return cipher.doFinal(data.getBytes());
    }


}
