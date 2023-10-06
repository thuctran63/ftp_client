package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.web.client.Client;
import com.example.web.database.Repository.FileTransferRepos;
import com.example.web.database.entity.FileTransfer;

import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    @Autowired
    FileTransferRepos fileTransferRepos;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        System.out.println("Địa chỉ IP của máy khách: " + clientIp);
        return "index";
    }

    @GetMapping("/SignUp")
    public String SignUp() {
        return "SignUp";
    }

    @GetMapping("/SignIn")
    public String SignIn() {
        return "SignIn";
    }

    @GetMapping("/home")
    public String home() {
        return "home";
    }

    @GetMapping("/sendfile")
    public String upload() {

        Client client = new Client();
        client.doConnections();
        return "sendfile";
    }

    @PostMapping("/sendfile")
    public String saveFileTransfer(HttpServletRequest request,@RequestParam("username_reciver") String usernameReceiver, @RequestParam("file") String file, @RequestParam("message") String message, Model model) {

        String clientIp = request.getRemoteAddr();
        fileTransferRepos.save( new FileTransfer(file, clientIp, "192.168.9.6"));
        model.addAttribute("successMessage", "Dữ liệu đã được lưu thành công!");
        return "sendFile";
    }
}
