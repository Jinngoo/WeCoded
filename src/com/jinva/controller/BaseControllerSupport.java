package com.jinva.controller;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;

public class BaseControllerSupport {

    protected Log logger = LogFactory.getLog(getClass());

    protected User getUser(HttpSession session) {
        return (User) session.getAttribute(JinvaConsts.USER);
    }

    protected void setUser(HttpSession session, User user) {
        session.setAttribute(JinvaConsts.USER, user);
    }

    protected String getUserId(HttpSession session) {
        User user = (User) session.getAttribute(JinvaConsts.USER);
        return user.getId();
    }

    protected Map<String, Object> getValidParameterMap(HttpServletRequest request, String prefix) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> validParams = new HashMap<String, Object>();
        for (Entry<String, String[]> entry : params.entrySet()) {
            String key = entry.getKey();
            if (key.contains(prefix)) {
                validParams.put(key.split("_")[1], entry.getValue());
            }
        }
        return validParams;
    }


        
}
