package com.jinva.action;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;

import com.jinva.bean.datamodel.Dish;
import com.jinva.bean.datamodel.Group;
import com.jinva.bean.datamodel.GroupProvider;
import com.jinva.bean.datamodel.Order;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.Restaurant;
import com.jinva.bean.datamodel.User;
import com.jinva.consts.JinvaConsts;
import com.jinva.service.JinvaService;
import com.jinva.service.MessageService;

public class AjaxAction extends BaseActionSupport {

	/**
     * 
     */
	private static final long serialVersionUID = 8308176686963641697L;

	private Map<String, Object> result;

	@Autowired
	private JinvaService jinvaService;
	
	@Autowired
	private MessageService messageService;

	/**
	 * 
	 * @return
	 */
	public String loadGroup(){
		setResult(new HashMap<String, Object>());
		String id = request.getParameter("id");
		if(id != null){
			Group group = jinvaService.get(Group.class, id);
			result.put("group", group);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String loadRestaurant(){
		setResult(new HashMap<String, Object>());
		String id = request.getParameter("id");
		if(id != null){
			Restaurant restaurant = jinvaService.get(Restaurant.class, id);
			result.put("restaurant", restaurant);
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String loadDish(){
		setResult(new HashMap<String, Object>());
		String id = request.getParameter("id");
		if(id != null){
			Dish dish = jinvaService.get(Dish.class, id);
			result.put("dish", dish);
		}
		return SUCCESS;
	}
	
	public String provideMeal(){
		setResult(new HashMap<String, Object>());
		String choosenGroups = request.getParameter("choosenGroups");
		String choosenRestaurants = request.getParameter("choosenRestaurants");
		
		OrderProvider orderProvider = new OrderProvider();
		orderProvider.setCreateDate(new Date());
		orderProvider.setProvideUserId(getUserId());
//		orderProvider.setReceiveGroups(choosenGroups);
		orderProvider.setRestaurants(choosenRestaurants);
		orderProvider.setStatus(OrderProvider.STATUS_OFFER);
		
		String orderProviderId = (String) jinvaService.save(orderProvider);
		for(String groupId : choosenGroups.split(",")){
			GroupProvider groupProvider = new GroupProvider();
			groupProvider.setGroupId(groupId);
			groupProvider.setOrderProviderId(orderProviderId);
			jinvaService.save(groupProvider);
		}
		
		if(orderProviderId != null){
			messageService.sendMessage(orderProvider, new String[]{getUserId()});
			result.put("code", "success");
		}else{
			result.put("code", "error");
		}
		
		return SUCCESS;
	}
	
	public String placeOrder(){
		setResult(new HashMap<String, Object>());
		String orderProviderId = request.getParameter("orderProviderId");
		String dishJsonStr = request.getParameter("dishJson");
		jinvaService.delete(Order.class, new String[]{"userId", "providerId"}, new Object[]{getUserId(), orderProviderId});
		for(String dishStr : dishJsonStr.split("&")){
			String dishId = dishStr.split("=")[0];
			String dishCount = dishStr.split("=")[1];
			if("0".equals(dishCount)){
				continue;
			}
			Order order = new Order();
			order.setDishId(dishId);
			order.setDishNum(Integer.valueOf(dishCount));
			order.setProviderId(orderProviderId);
			order.setUserId(getUserId());
			jinvaService.save(order);
		}
		result.put("code", "success");
		return SUCCESS;
	}
	
	public String cancelProvide(){
		setResult(new HashMap<String, Object>());
		String orderProviderId = request.getParameter("orderProviderId");
		jinvaService.cancelProvide(orderProviderId);
		result.put("code", "success");
		return SUCCESS;
	}
	
	public String finishProvide(){
		setResult(new HashMap<String, Object>());
		String orderProviderId = request.getParameter("orderProviderId");
		OrderProvider orderProvider = jinvaService.get(OrderProvider.class, orderProviderId);
		if(orderProvider != null){
			orderProvider.setStatus(OrderProvider.STATUS_END);
			jinvaService.update(orderProvider);
			result.put("code", "success");
		}else{
			result.put("code", "error");
		}
		return SUCCESS;
	}
	
	public String updateBasicInfo(){
		setResult(new HashMap<String, Object>());
		String nickname = getRequest().getParameter("nickname");
		String password = getRequest().getParameter("password");
		User user = jinvaService.get(User.class, getUserId());
		if(user != null){
			user.setNickname(nickname);
			if(StringUtils.isNotBlank(nickname)){
				user.setNickname(nickname);
			}
			if(StringUtils.isNotBlank(password)){
				user.setPassword(password);
			}
			jinvaService.update(user);
			session.setAttribute(JinvaConsts.USER_NICKNAME, user.getNickname());
			result.put("code", "success");
		}else{
			result.put("code", "error");
			result.put("message", "User not found");
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String copyRestaurant() {
		setResult(new HashMap<String, Object>());
		String restaurantId = getRequest().getParameter("id");
		Restaurant restaurant = jinvaService.get(Restaurant.class, restaurantId);
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
				cloneRestaurant.setOwnerId(getUserId());
				Object newRestaurantId = jinvaService.save(cloneRestaurant);
				if (newRestaurantId != null) {
					//save success, begin copying restaurant image ~.~
					jinvaService.copyFile(UploadAction.RESTAURANT_AVATAR_PATH, restaurantId, newRestaurantId.toString());
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
								jinvaService.copyFile(UploadAction.DISH_AVATAR_PATH, dish.getId(), newDishId.toString());
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
		return SUCCESS;
	}
	
	
	/**
	 * 
	 * @return
	 */
	public String validateName(){
		setResult(new HashMap<String, Object>());
		String name = request.getParameter("name");
		result.put("access", jinvaService.getUser(name) == null);
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public String signup() throws UnsupportedEncodingException{
		setResult(new HashMap<String, Object>());
		String name = request.getParameter("name");
		String password = request.getParameter("password");
		String nickname = request.getParameter("nickname");
		nickname = URLDecoder.decode(URLDecoder.decode(nickname, "utf-8"), "utf-8");

		if (jinvaService.getUser(name) != null) {
			result.put("code", "duplicate");
		} else {
			User user = new User(password, name, nickname);
			if (jinvaService.save(user) == null) {
				result.put("code", "error");
			} else {
				result.put("code", "success");
			}
		}
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String login() {
		setResult(new HashMap<String, Object>());
		String name = request.getParameter("username");
		String pass = request.getParameter("password");
		User user = jinvaService.getUser(name);
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
		return SUCCESS;
	}
	
	/**
	 * 
	 * @return
	 */
	public String logout(){
		session.setAttribute(JinvaConsts.USER_ID, null);
		session.setAttribute(JinvaConsts.USER_NAME, null);
		session.setAttribute(JinvaConsts.USER_NICKNAME, null);
		return SUCCESS;
	}

	
	public String test(){
		
		return SUCCESS;
	}
	
	public Map<String, Object> getResult() {
		return result;
	}

	public void setResult(Map<String, Object> result) {
		this.result = result;
	}

}
