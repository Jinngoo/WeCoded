package com.jinva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jinva.controller.base.BaseControllerSupport;

@Controller
@RequestMapping("/news")
public class NewsController extends BaseControllerSupport {

    @RequestMapping(value = { "", "/" })
    public String index() {
        return "news/index";
    }

}
