package com.jinva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/news")
public class NewsController {

    @RequestMapping(value = { "", "/" })
    public String index() {
        return "news/index";
    }
    
}
