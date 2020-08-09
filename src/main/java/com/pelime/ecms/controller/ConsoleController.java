package com.pelime.ecms.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/console")
public class ConsoleController {

    @RequestMapping("user")
    public String user(){
        return "/console/userManager";
    }
}
