package com.ll.ch03_10;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class HomeController {
    @Value("${custom.site.name}")
    private String siteName;

    @GetMapping("/")
    @ResponseBody
    public String showMain(){
        return "Hello, World! on " +siteName;
    }
}
