package com.jinva.controller;

import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;

public class BaseControllerSupport {

    protected Log logger = LogFactory.getLog(getClass());
    
    protected String getUserId(HttpSession session){
        User user = (User) session.getAttribute(JinvaConsts.USER);
        return user.getId();
    }
    
}
