package com.jinva.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.bean.datamodel.Group;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.service.JinvaService;

@Controller
@RequestMapping("/dining")
public class DiningController {

    @Autowired
    private JinvaService jinvaService;
    
    @RequestMapping(value = { "", "/" })
    public String index() {
        return "dining/dining";
    }

    @RequestMapping(value = "orderProviderList", method = RequestMethod.GET)
    public String orderProviderList(HttpServletRequest request) {
        String userId = "1";//"getUserId()"
        List<OrderProvider> orderProviderList = jinvaService.getOrderProviderList(0, -1, userId);
        jinvaService.parseOrderProviderName(orderProviderList, new HashMap<String, String>());
        jinvaService.parseOrderProviderGroup(orderProviderList);
        jinvaService.parseOrderProviderRestaurant(orderProviderList, new HashMap<String, String>());
        request.setAttribute("orderProviderList", orderProviderList);
        
        return index();
    }
    
    @RequestMapping(value = "groupList", method = RequestMethod.GET)
    public String groupList(HttpServletRequest request) {
        String userId = "1";//"getUserId()"
        List<Group> myGroupList = jinvaService.getMyGroupList(0, -1, userId);
        List<Group> joinedGroupList = jinvaService.getJoinedGroupList(0, -1, userId);
        List<Group> otherGroupList = jinvaService.getOtherGroupList(0, -1, userId);
        Map<String, String> cache = new HashMap<String, String>();
        jinvaService.parseGroupOwnerName(myGroupList, cache);
        jinvaService.parseGroupOwnerName(joinedGroupList, cache);
        jinvaService.parseGroupOwnerName(otherGroupList, cache);
        jinvaService.parseGroupMemberCount(myGroupList);
        jinvaService.parseGroupMemberCount(joinedGroupList);
        jinvaService.parseGroupMemberCount(otherGroupList);
        request.setAttribute("myGroupList", myGroupList);
        request.setAttribute("joinedGroupList", joinedGroupList);
        request.setAttribute("otherGroupList", otherGroupList);
        return index();
    }

}
