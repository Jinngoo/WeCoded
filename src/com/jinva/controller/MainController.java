package com.jinva.controller;

import java.util.HashMap;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;
import com.jinva.service.JinvaService;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private JinvaService jinvaService;
    
    @RequestMapping(value = { "", "/" })
    public String index(HttpSession session, HttpServletRequest request) {
        String serverInfo = request.getServletContext().getServerInfo();
        request.setAttribute("serverInfo", serverInfo);
        request.setAttribute("test", "TODO test static field");

        User user = (User) session.getAttribute(JinvaConsts.USER);
        if (user == null) {
            return "login";
        }

        return "main";
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> login(HttpSession session,HttpServletRequest request) {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        User user = jinvaService.getUser(name);
        JSONObject result = new JSONObject();
        if (user == null) {
            result.put("code", "nouser");
        } else if (!pass.equals(user.getPassword())) {
            result.put("code", "wrongpass");
        } else {
            session.setAttribute(JinvaConsts.USER_ID, user.getId());
            session.setAttribute(JinvaConsts.USER_NAME, user.getName());
            session.setAttribute(JinvaConsts.USER_NICKNAME, user.getNickname());
            result.put("code", "success");
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }

}
