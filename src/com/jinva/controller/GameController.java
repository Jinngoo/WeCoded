package com.jinva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jinva.controller.base.BaseControllerSupport;

@Controller
@RequestMapping("/game")
public class GameController extends BaseControllerSupport {

    @RequestMapping(value = "")
    public String index() {
        return "game/index";
    }

}
