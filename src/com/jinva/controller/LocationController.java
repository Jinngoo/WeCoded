package com.jinva.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jinva.controller.base.BaseControllerSupport;

@Controller
@RequestMapping("/location")
public class LocationController extends BaseControllerSupport {

    @RequestMapping(value = "")
    public String index() {
        return "location/index";
    }

}
