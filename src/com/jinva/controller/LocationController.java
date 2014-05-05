package com.jinva.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.jinva.controller.base.BaseControllerSupport;

@Controller
@RequestMapping("/location")
public class LocationController extends BaseControllerSupport {

    private boolean locatorControl;
    private String locatorUrl;
    
    @Value("#{propertiesReader[locatorControl]}")
    public void setLocatorControl(boolean locatorControl) {
        this.locatorControl = locatorControl;
    }
    
    @Value("#{propertiesReader[locatorUrl]}")
    public void setLocatorUrl(String locatorUrl) {
        this.locatorUrl = locatorUrl;
    }
    
    @RequestMapping(value = "")
    public String index(HttpServletRequest request) {
        boolean isAdmin = isAdmin(getUser(request.getSession()).getName());
        if (locatorControl && !isAdmin) {
            request.setAttribute("message", "你无权访问滴丫~");
            return "error/error";
        } else {
            request.setAttribute("locatorUrl", locatorUrl);
            return "location/index";
        }
    }

}
