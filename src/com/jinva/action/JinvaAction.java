package com.jinva.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jinva.bean.datamodel.Dish;
import com.jinva.bean.datamodel.Group;
import com.jinva.bean.datamodel.Order;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.Restaurant;
import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;
import com.jinva.service.JinvaService;

public class JinvaAction extends BaseActionSupport{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5884003876523827453L;

	@Autowired
	private JinvaService jinvaService;
	
	/**
	 * @return
	 * @throws IOException 
	 */
	public String init(){
	    boolean isLogin = hasSessionAttr(JinvaConsts.USER_ID);
		if(isLogin){
		    return "main";
		}else{
			request.setAttribute("title", "网上在线无敌超级订腰子信息管理自动化云平台");
		    return "login";
		}
	}
	
	public String news(){
		
		setPage("jsp/main/news");
		return SUCCESS;
	}
	
	public String message(){
		
		setPage("jsp/main/message");
		return SUCCESS;
	}
	
	public String dining(){
		String active = request.getParameter("active");
		if(StringUtils.isBlank(active)){
			active = "orderProvider";
		}
		String userId = getUserId();
		if(active.equals("orderProvider")){
			List<OrderProvider> orderProviderList = jinvaService.getOrderProviderList(0, -1, userId);
			jinvaService.parseOrderProviderName(orderProviderList, new HashMap<String, String>());
			jinvaService.parseOrderProviderGroup(orderProviderList);
			jinvaService.parseOrderProviderRestaurant(orderProviderList, new HashMap<String, String>());
			request.setAttribute("orderProviderList", orderProviderList);
		}else if(active.equals("group")){
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
		}else if(active.equals("restaurant")){
			List<Restaurant> restaurantList = jinvaService.getMyRestaurantList(0, -1, userId);
			List<Restaurant> otherRestaurantList = jinvaService.getOtherRestaurantList(0, -1, userId);
			request.setAttribute("myRestaurantList", restaurantList);
			request.setAttribute("otherRestaurantList", otherRestaurantList);
		}
		setPage("jsp/main/dining");
		return SUCCESS;
	}
	
	public String setting(){
		String userId = (String) session.getAttribute(JinvaConsts.USER_ID);
		User user = jinvaService.get(User.class, userId);
		request.setAttribute("user", user);
		setPage("jsp/main/setting");
		return SUCCESS;
	}
	
	public String dish(){
		String restaurantId = request.getParameter("restaurantId");
		Restaurant restaurant = jinvaService.get(Restaurant.class, restaurantId);
		restaurant.setOwnerName(jinvaService.getUserName(restaurant.getOwnerId()));
		List<Dish> dishList = jinvaService.getDishList(0, -1, restaurantId);
		request.setAttribute("restaurant", restaurant);
		request.setAttribute("dishList", dishList);
		setPage("jsp/main/dish");
		return SUCCESS;
	}
	
	
	public String member(){
		String groupId = request.getParameter("groupId");
		Group group = jinvaService.get(Group.class, groupId);
		group.setOwnerName(jinvaService.getUserName(group.getOwnerId()));
		group.setMemberCount(jinvaService.getGroupMemberCount(groupId));
		List<User> memberList = jinvaService.getGroupMemberList(groupId, group.getOwnerId());
		request.setAttribute("group", group);
		request.setAttribute("memberList", memberList);
		setPage("jsp/main/member");
		return SUCCESS;
	}
	
	public String provideMeal(){
		String userId = getUserId();
		List<Group> myGroupList = jinvaService.getMyGroupList(0, -1, userId);
		List<Group> joinedGroupList = jinvaService.getJoinedGroupList(0, -1, userId);
		Map<String, String> cache = new HashMap<String, String>();
		jinvaService.parseGroupOwnerName(myGroupList, cache);
		jinvaService.parseGroupOwnerName(joinedGroupList, cache);
		jinvaService.parseGroupMemberCount(myGroupList);
		jinvaService.parseGroupMemberCount(joinedGroupList);
		
		List<Restaurant> myRestaurantList = jinvaService.getMyRestaurantList(0, -1, userId);
		jinvaService.parseRestaurantOwnerName(myRestaurantList, new HashMap<String, String>());
		
		request.setAttribute("myGroupList", myGroupList);
		request.setAttribute("joinedGroupList", joinedGroupList);
		request.setAttribute("myRestaurantList", myRestaurantList);
		
		setPage("jsp/main/provideMeal");
		return SUCCESS;
	}
	
	public String order(){
		String orderProviderId = request.getParameter("orderProviderId");
		List<Restaurant> restaurantList = jinvaService.getProvideRestaurant(orderProviderId);
		Map<String, List<Dish>> restaurantDishMap = new HashMap<String, List<Dish>>();
		Map<String, Restaurant> restaurantMap = new HashMap<String, Restaurant>();
		for(Restaurant restaurant : restaurantList){
			List<Dish> dishList = jinvaService.getDishList(0, -1, restaurant.getId());
			restaurantDishMap.put(restaurant.getId(), dishList);
			restaurantMap.put(restaurant.getId(), restaurant);
		}
		List<Order> orderList = jinvaService.getOrderList(orderProviderId, getUserId());
		
		request.setAttribute("orderProviderId", orderProviderId);
		request.setAttribute("orderList", orderList);
		request.setAttribute("restaurantMap", restaurantMap);
		request.setAttribute("restaurantDishMap", restaurantDishMap);
		setPage("jsp/main/order");
		return SUCCESS;
	}
	
	public String orderList(){
		String orderProviderId = request.getParameter("orderProviderId");
		List<Order> orderList = jinvaService.getOrderList(orderProviderId, null);
		jinvaService.parseOrderList(orderList);
		OrderProvider orderProvider = jinvaService.get(OrderProvider.class, orderProviderId);
		request.setAttribute("orderList", orderList);
		request.setAttribute("orderProvider", orderProvider);
		setPage("jsp/main/orderList");
		return SUCCESS;
	}
	
}
