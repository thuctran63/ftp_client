package com.example.web;

import java.util.Arrays;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import com.example.web.client.Client;
import com.example.web.model.Host;
import jakarta.servlet.http.HttpServletRequest;

@Controller
public class WebController {

    Client client = null;

    @GetMapping("/")
    public String index(HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        System.out.println("Địa chỉ IP của máy khách: " + clientIp);
        return "index";
    }

    @GetMapping("/login")
    public String login(Model model) {
        model.addAttribute("host", new Host());
        return "login";
    }

    @PostMapping("/login")
    public String login(@ModelAttribute("host") Host host) {
        try {
            client = new Client("localhost", 2023, host.getIpHost() , host.getPort());
            if (client.connect()) {
                return "redirect:/home";
            }
            return "redirect:/login";
        } catch (Exception e) {

        }
        return "redirect:/login";
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
    public String sendfile() {
        return "sendfile";
    }

    @PostMapping("/sendfile")
    public String sendfile(@RequestParam("filepath") String filepath, @RequestParam("target_dir") String pathSave, Model model) {
        if (!filepath.isEmpty()) {
            if(client.sendFile(filepath, pathSave)){
                model.addAttribute("message", "SUCCESS");
                return "sendfile";
            }
            model.addAttribute("message", "FAIL");
            return "sendfile";
        } else {
            model.addAttribute("message", "Enter file path before send");
            return "sendfile";
        }
    }

    @GetMapping("/showfile")
    public String showfile(Model model) {
        List<String> list = Arrays.asList("C:", "D:", "E:");
        model.addAttribute("list", list);
        return "showfile";
    }

    @PostMapping("/showfile")
    public String getFile(@RequestParam(name = "path", defaultValue = "") String path, Model model) {
        String[] arr = (client.getListFile(path)).split("\n");
        List<String> list = Arrays.asList(arr);
        model.addAttribute("list", list);
        model.addAttribute("lastPath", path);
        return "showfile";
    }



}
