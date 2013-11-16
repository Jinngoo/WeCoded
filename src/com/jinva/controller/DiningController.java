package com.jinva.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.Restaurant;
import com.jinva.bean.datamodel.Team;
import com.jinva.bean.datamodel.UserTeam;
import com.jinva.service.JinvaService;
import com.jinva.util.CommonUtil;

@Controller
@RequestMapping("/dining")
public class DiningController extends BaseControllerSupport{

    @Autowired
    private JinvaService jinvaService;
    
    @RequestMapping(value = "")
    public String index() {
        return "dining/index";
    }
    
    @RequestMapping(value = "/{tab}")
    public String index(@PathVariable("tab") String tab, HttpServletRequest request) {
        request.setAttribute("tab", tab);
        return "dining/index";
    }

    public String orderProviderList(HttpServletRequest request, HttpSession session) {
        List<OrderProvider> orderProviderList = jinvaService.getOrderProviderList(0, -1, getUserId(session));
        jinvaService.parseOrderProviderName(orderProviderList, new HashMap<String, String>());
        jinvaService.parseOrderProviderTeam(orderProviderList);
        jinvaService.parseOrderProviderRestaurant(orderProviderList, new HashMap<String, String>());
        request.setAttribute("orderProviderList", orderProviderList);
        
        return index();
    }
    
    @RequestMapping(value = "teamList", method = RequestMethod.GET)
    public String teamList(HttpServletRequest request, HttpSession session) {
        String userId = getUserId(session);
        List<Team> myTeamList = jinvaService.getMyTeamList(0, -1, userId);
        List<Team> joinedTeamList = jinvaService.getJoinedTeamList(0, -1, userId);
        List<Team> otherTeamList = jinvaService.getOtherTeamList(0, -1, userId);
        Map<String, String> cache = new HashMap<String, String>();
        jinvaService.parseTeamOwnerName(myTeamList, cache);
        jinvaService.parseTeamOwnerName(joinedTeamList, cache);
        jinvaService.parseTeamOwnerName(otherTeamList, cache);
        jinvaService.parseTeamMemberCount(myTeamList);
        jinvaService.parseTeamMemberCount(joinedTeamList);
        jinvaService.parseTeamMemberCount(otherTeamList);
        request.setAttribute("myTeamList", myTeamList);
        request.setAttribute("joinedTeamList", joinedTeamList);
        request.setAttribute("otherTeamList", otherTeamList);
        return index();
    }
    
    @RequestMapping(value = "loadTeam", method = RequestMethod.GET)
    public ResponseEntity<Team> loadTeam(HttpServletRequest request){
        String id = request.getParameter("id");
        Team team = jinvaService.get(Team.class, id);
        return new ResponseEntity<Team>(team, HttpStatus.OK);
    }
    
    @RequestMapping(value = "deleteTeam/{id}", method = RequestMethod.GET)
    public String deleteTeam(@PathVariable ("id") String id, HttpServletRequest request, HttpSession session) throws InterruptedException{
        jinvaService.delete(Team.class, id);
        jinvaService.delete(UserTeam.class, new String[]{"teamId"}, new Object[]{id});
        return teamList(request, session);
    }

    @RequestMapping(value = "cancelProvide/{id}", method = RequestMethod.POST)
    public ResponseEntity<String> cancelProvide(@PathVariable ("id") String id, HttpServletRequest request){
        jinvaService.cancelProvide(id);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
    @RequestMapping(value = "loadRestaurant/{id}", method = RequestMethod.GET)
    public ResponseEntity<Restaurant> loadRestaurant(@PathVariable ("id") String id, HttpServletRequest request) {
        Restaurant restaurant = jinvaService.get(Restaurant.class, id);
        return new ResponseEntity<Restaurant>(restaurant, HttpStatus.OK);
    }
    
    @RequestMapping(value = "joinTeam/{id}", method = RequestMethod.GET)
    public String joinTeam(@PathVariable ("id") String id, HttpServletRequest request, HttpSession session) throws InterruptedException{
        UserTeam userTeam = new UserTeam();
        userTeam.setEnterDate(new Date());
        userTeam.setTeamId(id);
        userTeam.setUserId(getUserId(session));
        jinvaService.save(userTeam);
        return teamList(request, session);
    }
    
    @RequestMapping(value = "quitTeam/{id}", method = RequestMethod.GET)
    public String quitTeam(@PathVariable ("id") String id, HttpServletRequest request, HttpSession session) throws InterruptedException{
        jinvaService.deleteUserTeam(getUserId(session), id);
        return teamList(request, session);
    }
    
    @RequestMapping(value = "saveTeam", method = RequestMethod.POST)
    public String saveTeam(HttpServletRequest request, HttpSession session) throws InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
        Map<String, Object> parameterMap = CommonUtil.getValidParameterMap(request, "group");
        String teamId = CommonUtil.toString(parameterMap.get("id"));
        String userId = getUserId(session);
        if (StringUtils.isEmpty(teamId)) {// insert
            Team team = Team.class.newInstance();
            BeanUtils.populate(team, parameterMap);
            team.setOwnerId(userId);
            teamId = (String) jinvaService.save(team);
            UserTeam userTeam = new UserTeam();
            userTeam.setEnterDate(new Date());
            userTeam.setTeamId(teamId);
            userTeam.setUserId(userId);
            jinvaService.save(userTeam);
        } else {//update
            Team team = jinvaService.get(Team.class, teamId);
            BeanUtils.populate(team, parameterMap);
            jinvaService.update(team);
        }
        return teamList(request, session);
    }
    
}
