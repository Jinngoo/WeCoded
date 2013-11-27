package com.jinva.controller;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.jinva.bean.datamodel.Dish;
import com.jinva.bean.datamodel.OrderForm;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.Restaurant;
import com.jinva.bean.datamodel.Team;
import com.jinva.bean.datamodel.TeamProvider;
import com.jinva.bean.datamodel.User;
import com.jinva.bean.datamodel.UserTeam;
import com.jinva.consts.JinvaConsts;
import com.jinva.service.JinvaService;
import com.jinva.service.storage.IStorage;
import com.jinva.util.CodeSupport;
import com.jinva.util.CommonUtil;
import com.jinva.util.StorageUtil;

@Controller
@RequestMapping("/dining")
public class DiningController extends BaseControllerSupport {

    @Autowired
    private JinvaService jinvaService;

    @Autowired
    private IStorage storage;
    
    @RequestMapping(value = "")
    public String index() {
        return "dining/index";
    }
    
    @RequestMapping(value = "/{tab}")
    public String index(@PathVariable("tab") String tab, HttpServletRequest request) {
        request.setAttribute("tab", tab);
        return "dining/index";
    }

    @RequestMapping(value = "orderProviderCount", method = RequestMethod.GET)
    public ResponseEntity<JSONObject> orderProviderCount(HttpSession session) {
        int count = jinvaService.getOrderProviderCount(getUserId(session));
        JSONObject result = new JSONObject();
        result.put("count", count);
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "orderProviderList", method = RequestMethod.GET)
    public String orderProviderList(HttpServletRequest request, HttpSession session) {
        List<OrderProvider> orderProviderList = jinvaService.getOrderProviderList(0, -1, getUserId(session));
        jinvaService.parseOrderProviderName(orderProviderList, new HashMap<String, String>());
        jinvaService.parseOrderProviderTeam(orderProviderList);
        jinvaService.parseOrderProviderRestaurant(orderProviderList, new HashMap<String, String>());
        request.setAttribute("orderProviderList", orderProviderList);
        request.setAttribute("orderProviderStatusCode", CodeSupport.getOrderProviderStatusCode(request));
        
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
    
    @RequestMapping(value = "restaurantList", method = RequestMethod.GET)
    public String restaurantList(HttpServletRequest request, HttpSession session) throws InterruptedException{
        String userId = getUserId(session);
        List<Restaurant> myRestaurantList = jinvaService.getMyRestaurantList(0, -1, userId);
        List<Restaurant> publicRestaurantList = jinvaService.getPublicRestaurantList(0, -1, userId);
        List<Restaurant> otherRestaurantList = jinvaService.getOtherRestaurantList(0, -1, userId);
        request.setAttribute("myRestaurantList", myRestaurantList);
        request.setAttribute("publicRestaurantList", publicRestaurantList);
        request.setAttribute("otherRestaurantList", otherRestaurantList);
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
    
    @RequestMapping(value = "deleteRestaurant/{id}", method = RequestMethod.GET)
    public String deleteRestaurant(@PathVariable ("id") String id, HttpServletRequest request, HttpSession session) throws InterruptedException{
        //delete dish
        List<Dish> dishList = jinvaService.select(Dish.class, new String[] { "restaurantId" }, new Object[] { id });
        String dishPath = StorageUtil.getPathByType(JinvaConsts.UPLOAD_TYPE_DISH_AVATAR);
        for (Dish dish : dishList) {
            storage.delete(dishPath, dish.getId());
            jinvaService.delete(dish);
        }
        //delete restaurant
        String restaurantPath = StorageUtil.getPathByType(JinvaConsts.UPLOAD_TYPE_RESTNURANT_AVATAR);
        storage.delete(restaurantPath, id);
        jinvaService.delete(Restaurant.class, id);
        //TODO delete OrderProvider
        return restaurantList(request, session);
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
        Map<String, Object> parameterMap = getValidParameterMap(request, "group");
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
    
    @RequestMapping(value = "saveRestaurant", method = RequestMethod.POST)
    public String saveRestaurant(HttpServletRequest request, HttpSession session) throws InterruptedException, IllegalAccessException, InvocationTargetException, NoSuchMethodException, InstantiationException{
        Map<String, Object> parameterMap = getValidParameterMap(request, "restaurant");
        String restaurantId = CommonUtil.toString(parameterMap.get("id"));
        String userId = getUserId(session);
        if (StringUtils.isEmpty(restaurantId)) {// insert
            Restaurant restaurant = Restaurant.class.newInstance();
            BeanUtils.populate(restaurant, parameterMap);
            restaurant.setOwnerId(userId);
            jinvaService.save(restaurant);
        } else {//update
            Restaurant restaurant = jinvaService.get(Restaurant.class, restaurantId);
            BeanUtils.populate(restaurant, parameterMap);
            jinvaService.update(restaurant);
        }
        return restaurantList(request, session);
    }
    
    @RequestMapping(value = {"teamMember/{teamId}/{backUrl}"})
    public String teamMember(@PathVariable("teamId") String teamId, @PathVariable("backUrl") String backUrl, HttpSession session, HttpServletRequest request){
        Team team = jinvaService.get(Team.class, teamId);
        team.setOwnerName(jinvaService.getUserName(team.getOwnerId()));
        team.setMemberCount(jinvaService.getTeamMemberCount(teamId));
        List<User> memberList = jinvaService.getTeamMemberList(teamId, team.getOwnerId());
        request.setAttribute("team", team);
        request.setAttribute("memberList", memberList);
        request.setAttribute("backUrl", backUrl);
        return "dining/teamMember";
    }
    
    @RequestMapping(value = "restaurantMenu/{restaurantId}/{backUrl}")
    public String restaurantMenu(@PathVariable("restaurantId") String restaurantId, @PathVariable("backUrl") String backUrl, HttpSession session, HttpServletRequest request){
        Restaurant restaurant = jinvaService.get(Restaurant.class, restaurantId);
        restaurant.setOwnerName(jinvaService.getUserName(restaurant.getOwnerId()));
        List<Dish> dishList = jinvaService.getDishList(0, -1, restaurantId);
        request.setAttribute("restaurant", restaurant);
        request.setAttribute("dishList", dishList);
        request.setAttribute("backUrl", backUrl);
        return "dining/menu";
    }
    
    @RequestMapping(value = "saveDish", method = RequestMethod.POST)
    public String saveDish(HttpSession session, HttpServletRequest request) throws InstantiationException, IllegalAccessException, InvocationTargetException, InterruptedException{
        Map<String, Object> parameterMap = getValidParameterMap(request, "dish");
        String dishId = CommonUtil.toString(parameterMap.get("id"));
        Dish dish = null;
        if (StringUtils.isEmpty(dishId)) {// insert
            dish = Dish.class.newInstance();
            BeanUtils.populate(dish, parameterMap);
            jinvaService.save(dish);
        } else {//update
            dish = jinvaService.get(Dish.class, dishId);
            BeanUtils.populate(dish, parameterMap);
            jinvaService.update(dish);
        }
        return restaurantMenu(dish.getRestaurantId(), null, session, request);
    }
    
    @RequestMapping(value = "loadDish/{id}", method = RequestMethod.GET)
    public ResponseEntity<Dish> loadDish(@PathVariable("id") String id){
        Dish dish = jinvaService.get(Dish.class, id);
        return new ResponseEntity<Dish>(dish, HttpStatus.OK);
    }
    
    @RequestMapping(value = "deleteDish/{id}", method = RequestMethod.GET)
    public String deleteDish(@PathVariable("id") String id, HttpSession session, HttpServletRequest request) throws InterruptedException{
        Dish dish = jinvaService.get(Dish.class, id);
        String restaurantId = null;
        if (dish != null) {
            restaurantId = dish.getRestaurantId();
            String dishPath = StorageUtil.getPathByType(JinvaConsts.UPLOAD_TYPE_DISH_AVATAR);
            storage.delete(dishPath, id);
            jinvaService.delete(dish);
        }
        return restaurantMenu(restaurantId, null, session, request);
    }
    
    @RequestMapping(value = "copyRestaurant/{restaurantId}", method = RequestMethod.POST)
    public ResponseEntity<JSONObject> copyRestaurant(@PathVariable("restaurantId") String restaurantId, HttpSession session) {
        Restaurant restaurant = jinvaService.get(Restaurant.class, restaurantId);
        JSONObject result = new JSONObject();
        if (restaurant != null) {
            Restaurant cloneRestaurant = null;
            try {
                cloneRestaurant = (Restaurant) BeanUtils.cloneBean(restaurant);
            } catch (Exception e) {
                logger.error("Clone restaurant error", e);
            }
            if (cloneRestaurant == null) {
                result.put("code", "error");
                result.put("message", "Clone restaurant fail");
            } else {
                //clone success, ready to save
                cloneRestaurant.setId(null);
                cloneRestaurant.setOwnerId(getUserId(session));
                Object newRestaurantId = jinvaService.save(cloneRestaurant);
                if (newRestaurantId != null) {
                    //save success, begin copying restaurant image ~.~
                    jinvaService.copyFile(JinvaConsts.RESTAURANT_AVATAR_PATH, restaurantId, newRestaurantId.toString());
                    //copydish
                    List<Dish> dishList = jinvaService.select(Dish.class, new String[]{"restaurantId"}, new Object[]{restaurantId});
                    try {
                        for(Dish dish : dishList){
                            Dish cloneDish = (Dish) BeanUtils.cloneBean(dish);
                            cloneDish.setId(null);
                            cloneDish.setRestaurantId(newRestaurantId.toString());
                            Object newDishId = jinvaService.save(cloneDish);
                            if(newDishId != null){
                                //copy dish image ~.~
                                jinvaService.copyFile(JinvaConsts.DISH_AVATAR_PATH, dish.getId(), newDishId.toString());
                            }
                        }
                    } catch (Exception e) {
                        logger.error("Clone dish error", e);
                    }
                    result.put("code", "success");
                } else {
                    result.put("code", "error");
                    result.put("message", "Save restaurant fail");
                }
            }
        } else {
            result.put("code", "error");
            result.put("message", "Restaurant not found");
        }
        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "provideMealPage/{backUrl}")
    public String provideMealPage(HttpSession session, HttpServletRequest request, @PathVariable("backUrl") String backUrl){
        String userId = getUserId(session);
        
        List<Team> teamList = jinvaService.getOptionalTeamList(0, -1, userId);
        
        jinvaService.parseTeamOwnerName(teamList, new HashMap<String, String>());
        jinvaService.parseTeamMemberCount(teamList);
        
        List<Restaurant> restaurantList = jinvaService.getOptionalRestaurantList(0, -1, userId);
        jinvaService.parseRestaurantOwnerName(restaurantList, new HashMap<String, String>());
        
        
        request.setAttribute("teamList", teamList);
        request.setAttribute("restaurantList", restaurantList);
        request.setAttribute("backUrl", backUrl);
        
        return "dining/provideMeal";
    }
    
    @RequestMapping(value = "provideMeal")
    public ResponseEntity<JSONObject> provideMeal(HttpServletRequest request, HttpSession session) {
        String choosenTeams = request.getParameter("choosenTeams");
        String choosenRestaurants = request.getParameter("choosenRestaurants");

        OrderProvider orderProvider = new OrderProvider();
        orderProvider.setCreateDate(new Date());
        orderProvider.setProvideUserId(getUserId(session));
        // orderProvider.setReceiveGroups(choosenGroups);
        orderProvider.setRestaurants(choosenRestaurants);
        orderProvider.setStatus(OrderProvider.STATUS_OFFER);

        String orderProviderId = (String) jinvaService.save(orderProvider);
        for (String teamId : choosenTeams.split(",")) {
            TeamProvider teamProvider = new TeamProvider();
            teamProvider.setTeamId(teamId);
            teamProvider.setOrderProviderId(orderProviderId);
            jinvaService.save(teamProvider);
        }

        JSONObject result = new JSONObject();
        if (orderProviderId != null) {
            // messageService.sendMessage(orderProvider, new
            // String[]{getUserId()});
            result.put("code", "success");
        } else {
            result.put("code", "error");
        }

        return new ResponseEntity<JSONObject>(result, HttpStatus.OK);
    }
    
    @RequestMapping(value = "joinOrder/{orderProviderId}/{backUrl}")
    public String joinOrder(HttpServletRequest request, HttpSession session, @PathVariable("orderProviderId") String orderProviderId, @PathVariable("backUrl") String backUrl){
        List<Restaurant> restaurantList = jinvaService.getProvideRestaurant(orderProviderId);
        Map<String, List<Dish>> restaurantDishMap = new HashMap<String, List<Dish>>();
        Map<String, Restaurant> restaurantMap = new HashMap<String, Restaurant>();
        for(Restaurant restaurant : restaurantList){
            List<Dish> dishList = jinvaService.getDishList(0, -1, restaurant.getId());
            restaurantDishMap.put(restaurant.getId(), dishList);
            restaurantMap.put(restaurant.getId(), restaurant);
        }
        List<OrderForm> orderList = jinvaService.getOrderList(orderProviderId, getUserId(session));
        
        request.setAttribute("orderProviderId", orderProviderId);
        request.setAttribute("orderList", orderList);
        request.setAttribute("restaurantMap", restaurantMap);
        request.setAttribute("restaurantDishMap", restaurantDishMap);
        request.setAttribute("backUrl", backUrl);
        return "dining/order";
    }

    @RequestMapping(value = "orderList/{orderProviderId}/{backUrl}")
    public String orderList(HttpServletRequest request, HttpSession session, @PathVariable("orderProviderId") String orderProviderId, @PathVariable("backUrl") String backUrl){
        List<OrderForm> orderList = jinvaService.getOrderList(orderProviderId, null);
        jinvaService.parseOrderList(orderList);
        OrderProvider orderProvider = jinvaService.get(OrderProvider.class, orderProviderId);
        request.setAttribute("orderList", orderList);
        request.setAttribute("orderProvider", orderProvider);
        request.setAttribute("backUrl", backUrl);
        
        String restaurants = orderProvider.getRestaurants();
        if(StringUtils.isNotBlank(restaurants)){
            List<Restaurant> restaurantList = new ArrayList<Restaurant>();
            for(String id : restaurants.split(",")){
                Restaurant res = jinvaService.get(Restaurant.class, id); //TODO 改成in
                restaurantList.add(res);
            }
            request.setAttribute("restaurantList", restaurantList);
        }
        
        return "dining/orderList";
    }
    
    @RequestMapping(value = "submitOrder", method = RequestMethod.POST)
    public ResponseEntity<String> submitOrder(HttpServletRequest request, HttpSession session) {
        String orderProviderId = request.getParameter("orderProviderId");
        String dishJsonStr = request.getParameter("dishJson");
        String userId = getUserId(session);
        jinvaService.delete(OrderForm.class, new String[] { "userId", "providerId" }, new Object[] { userId, orderProviderId });
        for (String dishStr : dishJsonStr.split("&")) {
            String dishId = dishStr.split("=")[0];
            String dishCount = dishStr.split("=")[1];
            if ("0".equals(dishCount)) {
                continue;
            }
            OrderForm order = new OrderForm();
            order.setDishId(dishId);
            order.setDishNum(Integer.valueOf(dishCount));
            order.setProviderId(orderProviderId);
            order.setUserId(userId);
            jinvaService.save(order);
        }
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
    @RequestMapping(value = "cancelProvide/{orderProviderId}", method = RequestMethod.POST)
    public ResponseEntity<String> cancelProvide(HttpServletRequest request, HttpSession session, @PathVariable("orderProviderId") String orderProviderId) {
        jinvaService.cancelProvide(orderProviderId);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }
    
    @RequestMapping(value = "finishProvide/{orderProviderId}", method = RequestMethod.POST)
    public ResponseEntity<String> finishProvide(HttpServletRequest request, HttpSession session, @PathVariable("orderProviderId") String orderProviderId) {
        OrderProvider orderProvider = jinvaService.get(OrderProvider.class, orderProviderId);
        orderProvider.setStatus(OrderProvider.STATUS_END);
        jinvaService.update(orderProvider);
        return new ResponseEntity<String>("success", HttpStatus.OK);
    }

}
