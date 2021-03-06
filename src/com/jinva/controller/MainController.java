package com.jinva.controller;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.ArrayList;
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

import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;
import com.jinva.controller.base.BaseControllerSupport;
import com.jinva.service.DiningService;
import com.jinva.service.JinvaService;

@Controller
@RequestMapping("/")
public class MainController extends BaseControllerSupport{

    @Autowired
    private JinvaService jinvaService;
    
    @Autowired
    private DiningService diningService;
    
    @RequestMapping(value = "test2")
    public String test2(HttpSession session) {
        diningService.test(getUserId(session));
        return "test2";
    }
    
    @RequestMapping(value="test")
    public String test(HttpServletRequest request){
        Integer pageSize = getInteger(request, "pageSize", 3);
        Integer pageNum = getInteger(request, "pageNum", 1);
        
        int fromIndex = (pageNum - 1) * pageSize;
        int toIndex = pageNum * pageSize;

        List<String> result = new ArrayList<String>();
        result.add("abc001");
        result.add("abc002");
        result.add("abc003");
        result.add("abc004");
        result.add("abc005");
        result.add("abc006");
        result.add("abc007");
        result.add("abc008");
        result.add("abc009");
        result.add("abc010");
        
        if(toIndex > result.size()){
            toIndex = result.size();
        }
        
        List<String> data = result.subList(fromIndex, toIndex);
        
        request.setAttribute("result", data);
        request.setAttribute("pageSize", pageSize);
        request.setAttribute("pageNum", pageNum);
        request.setAttribute("totalCount", result.size());
        return "main";
    }
    
    @RequestMapping(value = { "", "/" })
    public String index(HttpSession session) {
        return getUser(session) == null ? "redirect:/login" : "redirect:/dining";
    }
    
    @RequestMapping(value = "login", method = RequestMethod.GET)
    public String login(HttpSession session){
        User user = (User) session.getAttribute(JinvaConsts.USER);
        return user == null ? "login" : redirectIndex();
    }
    
    @RequestMapping(value = "login/{redirect}", method = RequestMethod.GET)
    public String login(HttpSession session, HttpServletRequest request, @PathVariable("redirect") String redirect) throws UnsupportedEncodingException{
        User user = (User) session.getAttribute(JinvaConsts.USER);
        request.setAttribute("redirect", redirect);
        return user == null ? "login" : redirectIndex();
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
    public ResponseEntity<String> signin(HttpSession session, HttpServletRequest request) {
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

}
