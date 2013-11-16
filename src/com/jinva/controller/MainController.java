package com.jinva.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.bean.datamodel.Team;
import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;
import com.jinva.service.JinvaService;

@Controller
@RequestMapping("/")
public class MainController {

    @Autowired
    private JinvaService jinvaService;
    
    @RequestMapping(value = "test/{id}/")
    public String test(@PathVariable ("id") String id, HttpServletRequest request){
        System.out.println(id);
        request.setAttribute("test", id);
        return "main";
    }
    
    @RequestMapping(value = { "", "/" })
    public String index(HttpSession session, HttpServletRequest request) {
        String serverInfo = request.getServletContext().getServerInfo();
        request.setAttribute("serverInfo", serverInfo);
        request.setAttribute("test", "123");

        User user = (User) session.getAttribute(JinvaConsts.USER);
        return user == null ? "redirect:/login" : "main";
    }
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpSession session){
        User user = (User) session.getAttribute(JinvaConsts.USER);
        return user == null ? "login" : "redirect:/";
    }
    
    @RequestMapping(value = "login/validateName", method = RequestMethod.POST)
    public ResponseEntity<Boolean> validateName(HttpServletRequest request){
        String name = request.getParameter("name");
        boolean access = jinvaService.getUser(name) == null;
        return new ResponseEntity<Boolean>(access, HttpStatus.OK);
    }
    
    @RequestMapping(value = "login/signup", method = RequestMethod.POST)
    public ResponseEntity<String> signup(HttpServletRequest request) throws UnsupportedEncodingException {
        String name = request.getParameter("name");
        String password = request.getParameter("password");
        String nickname = request.getParameter("nickname");
        nickname = URLDecoder.decode(URLDecoder.decode(nickname, "utf-8"), "utf-8");

        String code = null;
        if (jinvaService.getUser(name) != null) {
            code = "duplicate";
        } else {
            User user = new User(password, name, nickname);
            if (jinvaService.save(user) == null) {
                code = "error";
            } else {
                code = "success";
            }
        }
        return new ResponseEntity<String>(code, HttpStatus.OK);
    }
    
    
    @RequestMapping(value = "login/signin", method = RequestMethod.POST)
    public ResponseEntity<String> signin(HttpSession session,HttpServletRequest request) {
        String name = request.getParameter("username");
        String pass = request.getParameter("password");
        User user = jinvaService.getUser(name);
        String code = null;
        if (user == null) {
            code = "nouser";
        } else if (!pass.equals(user.getPassword())) {
            code = "wrongpass";
        } else {
            session.setAttribute(JinvaConsts.USER, user);
            code = "success";
        }
        return new ResponseEntity<String>(code, HttpStatus.OK);
    }
    
    @RequestMapping(value = "signout" )
    public String signout(HttpSession session) {
        session.removeAttribute(JinvaConsts.USER);
        return "redirect:/";
    }

    @RequestMapping(value = {"main/teamMember/{teamId}/{backUrl}"})
    public String teamMember(@PathVariable("teamId") String teamId, @PathVariable("backUrl") String backUrl, HttpSession session, HttpServletRequest request){
        Team team = jinvaService.get(Team.class, teamId);
        team.setOwnerName(jinvaService.getUserName(team.getOwnerId()));
        team.setMemberCount(jinvaService.getTeamMemberCount(teamId));
        List<User> memberList = jinvaService.getTeamMemberList(teamId, team.getOwnerId());
        request.setAttribute("team", team);
        request.setAttribute("memberList", memberList);
        request.setAttribute("backUrl", backUrl);
        return "main/teamMember";
    }
    
}
