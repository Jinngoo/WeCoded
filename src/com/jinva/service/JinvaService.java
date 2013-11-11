package com.jinva.service;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinva.bean.datamodel.Dish;
import com.jinva.bean.datamodel.DishParent;
import com.jinva.bean.datamodel.Group;
import com.jinva.bean.datamodel.GroupProvider;
import com.jinva.bean.datamodel.Order;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.Restaurant;
import com.jinva.bean.datamodel.User;
import com.jinva.bean.datamodel.UserGroup;
import com.jinva.dao.TheDao;
import com.jinva.service.storage.IStorage;
import com.jinva.util.CommonUtil;

@Component
public class JinvaService {

	@Autowired
	private TheDao theDao;
	
	@Autowired
    private IStorage storage;
	
	public Serializable save(Object entity){
		return theDao.save(entity);
	}
	
	public void saveOrUpdate(Object entity){
		theDao.saveOrUpdate(entity);
	}
	
	public void update(Object entity){
		theDao.update(entity);
	}
	
	public int bulkUpdate(String hql){
		return theDao.getHibernateTemplate().bulkUpdate(hql);
	}
	
	public int bulkUpdate(String hql, Object[] params){
		return theDao.getHibernateTemplate().bulkUpdate(hql, params);
	}
	
	public <T> T get(Class<T> entityClass, String id){
		if (StringUtils.isNotEmpty(id)) {
			return theDao.getHibernateTemplate().get(entityClass, id);
		} else {
			return null;
		}
	}
	
	public void delete(Object entity){
		theDao.getHibernateTemplate().delete(entity);
	}
	
	public void delete(Class<?> entityClass, String id){
		if (StringUtils.isNotEmpty(id)) {
			Object entity = theDao.getHibernateTemplate().get(entityClass, id);
			if(entity != null){
				theDao.getHibernateTemplate().delete(entity);
			}
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> select(String hql, Object[] params){
		return theDao.getHibernateTemplate().find(hql, params);
	}
	
	@SuppressWarnings("unchecked")
	public <T> List<T> select(Class<?> entityClass, String[] fieldNames, Object[] fieldValues) {
		DetachedCriteria c = DetachedCriteria.forClass(entityClass);
		for (int i = 0; i < fieldNames.length; i++) {
			c.add(Restrictions.eq(fieldNames[i], fieldValues[i]));
		}
		return theDao.getHibernateTemplate().findByCriteria(c);
	}
	
	public int delete(Class<?> entityClass, String[] fieldNames, Object[] fieldValues){
		String hql = "delete from " + entityClass.getName() + " where ";
		List<String> fieldList = new ArrayList<String>();
		for(String fieldName : fieldNames){
			fieldList.add(fieldName + "=?");
		}
		hql = hql + StringUtils.join(fieldList, " and ");
		return theDao.getHibernateTemplate().bulkUpdate(hql, fieldValues);
	}
	
	public User getUser(String name){
		return (User) theDao.findOneByFields(User.class, new String[]{"name"}, new Object[]{name});
	}
	
	public String getUserName(String id){
		User user = theDao.getHibernateTemplate().get(User.class, id);
		if(user != null){
			return user.getNickname();
		}else{
			return null;
		}
	}
	
	public long getGroupMemberCount(String groupId){
		return theDao.selectCount(UserGroup.class, new String[]{"groupId"}, new Object[]{groupId});
	}
	
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Group> getGroupList(int start, int size){
    	List<Group> result = (List<Group>) theDao.find(Group.class);
    	Map<String, String> cache = new HashMap<String, String>();
		parseGroupOwnerName(result, cache);
    	if(size == -1){
    		return result;
    	}else{
    		return (List<Group>) CommonUtil.fillBlankList(result, Group.class, size);
    	}
    }
    
    @SuppressWarnings("unchecked")
	public List<Group> getMyGroupList(int start, int size, String userId){
    	String hql = "from #Group where ownerId = ?";
    	hql = hql.replaceAll("#Group", Group.class.getName());
    	return theDao.getHibernateTemplate().find(hql, new Object[]{userId});
    }
    
    @SuppressWarnings("unchecked")
	public List<Group> getJoinedGroupList(int start, int size, String userId){
    	String hql = "from #Group where ownerId <> ? and id in (select groupId from #UserGroup where userId = ?)";
    	hql = hql.replaceAll("#Group", Group.class.getName());
    	hql = hql.replaceAll("#UserGroup", UserGroup.class.getName());
    	return theDao.getHibernateTemplate().find(hql, new Object[]{userId, userId});
    }
    
    @SuppressWarnings("unchecked")
	public List<Group> getOtherGroupList(int start, int size, String userId){
    	String hql = "from #Group where ownerId <> ? and id not in (select groupId from #UserGroup where userId = ?)";
    	hql = hql.replaceAll("#Group", Group.class.getName());
    	hql = hql.replaceAll("#UserGroup", UserGroup.class.getName());
    	return theDao.getHibernateTemplate().find(hql, new Object[]{userId, userId});
    }
    
    @SuppressWarnings("unchecked")
	public List<Restaurant> getMyRestaurantList(int start, int size, String userId){
    	String hql = "from #Restaurant where ownerId = ?";
    	hql = hql.replaceAll("#Restaurant", Restaurant.class.getName());
    	List<Restaurant> myRestaurantList = theDao.getHibernateTemplate().find(hql, new Object[]{userId});
    	this.parseRestaurantOwnerName(myRestaurantList, new HashMap<String, String>());
		this.parseRestaurantDishCount(myRestaurantList);
		return myRestaurantList;
    }
    
    @SuppressWarnings("unchecked")
    public List<Restaurant> getOtherRestaurantList(int start, int size, String userId){
    	String hql = "from #Restaurant where ownerId <> ?";
    	hql = hql.replaceAll("#Restaurant", Restaurant.class.getName());
    	List<Restaurant> myRestaurantList = theDao.getHibernateTemplate().find(hql, new Object[]{userId});
    	this.parseRestaurantOwnerName(myRestaurantList, new HashMap<String, String>());
    	this.parseRestaurantDishCount(myRestaurantList);
    	return myRestaurantList;
    }
    
    public void parseRestaurantOwnerName(List<Restaurant> restaurantList, Map<String, String> cache){
    	if(cache == null){
    		cache = new HashMap<String, String>();
    	}
    	for(Restaurant restaurant : restaurantList){
    		String ownerId = restaurant.getOwnerId();
    		if(StringUtils.isEmpty(ownerId)){
    			continue;
    		}
    		if(cache.containsKey(ownerId)){
    			restaurant.setOwnerName(cache.get(ownerId));
    		}else{
    			User user = theDao.getHibernateTemplate().get(User.class, ownerId);
    			if(user != null){
    				String ownerName = user.getNickname();
    				cache.put(ownerId, ownerName);
    				restaurant.setOwnerName(ownerName);
    			}else{
    				//TODO
    			}
    		}
    	}
    }
    
    public void parseGroupOwnerName(List<Group> groupList, Map<String, String> cache){
    	if(cache == null){
    		cache = new HashMap<String, String>();
    	}
    	for(Group group : groupList){
    		String ownerId = group.getOwnerId();
    		if(StringUtils.isEmpty(ownerId)){
    			continue;
    		}
    		if(cache.containsKey(ownerId)){
    			group.setOwnerName(cache.get(ownerId));
    		}else{
    			User user = theDao.getHibernateTemplate().get(User.class, ownerId);
    			if(user != null){
    				String ownerName = user.getNickname();
    				cache.put(ownerId, ownerName);
    				group.setOwnerName(ownerName);
    			}else{
    				//TODO
    			}
    		}
    	}
    }
    
    public void parseOrderProviderName(List<OrderProvider> orderProviderList, Map<String, String> cache){
    	if(cache == null){
    		cache = new HashMap<String, String>();
    	}
    	for(OrderProvider orderProvider : orderProviderList){
    		String userId = orderProvider.getProvideUserId();
    		if(StringUtils.isEmpty(userId)){
    			continue;
    		}
    		if(cache.containsKey(userId)){
    			orderProvider.setProvideUserName(cache.get(userId));
    		}else{
    			User user = theDao.getHibernateTemplate().get(User.class, userId);
    			if(user != null){
    				String ownerName = user.getNickname();
    				cache.put(userId, ownerName);
    				orderProvider.setProvideUserName(ownerName);
    			}else{
    				//TODO
    			}
    		}
    	}
    }
    
    @SuppressWarnings("unchecked")
	public void parseOrderProviderGroup(List<OrderProvider> orderProviderList){
    	for(OrderProvider orderProvider : orderProviderList){
    		String hql = "select name from #Group where id in (select groupId from #GroupProvider where orderProviderId = ?)";
    		hql = hql.replaceAll("#Group", Group.class.getName());
    		hql = hql.replaceAll("#GroupProvider", GroupProvider.class.getName());
    		List<String> groupNameList = theDao.getHibernateTemplate().find(hql, new Object[]{orderProvider.getId()});
    		orderProvider.setReceiveGroups(StringUtils.join(groupNameList, ","));
    	}
    }
    
    public void parseOrderProviderRestaurant(List<OrderProvider> orderProviderList, Map<String, String> cache){
    	if(cache == null){
    		cache = new HashMap<String, String>();
    	}
    	for(OrderProvider orderProvider : orderProviderList){
    		String receiveRestaurants = orderProvider.getRestaurants();
    		if(StringUtils.isBlank(receiveRestaurants)){
    			continue;
    		}
    		List<String> receiveRestaurantList = new ArrayList<String>();
    		for(String receiveRestaurantId : receiveRestaurants.split(",")){
    			if(cache.containsKey(receiveRestaurantId)){
    				receiveRestaurantList.add(cache.get(receiveRestaurantId));
    			}else{
    				Restaurant restaurant = theDao.getHibernateTemplate().get(Restaurant.class, receiveRestaurantId);
    				if(restaurant != null){
    					cache.put(receiveRestaurantId, restaurant.getName());
    					receiveRestaurantList.add(restaurant.getName());
    				}else{
    					//TODO
    				}
    			}
    		}
    		orderProvider.setRestaurants(StringUtils.join(receiveRestaurantList, ","));
    	}
    }
    
    @SuppressWarnings("unchecked")
	public Map<String, Dish> parseDishChildren(List<Dish> dishList){
    	Map<String, Dish> dishMap = new HashMap<String, Dish>();
    	for(Dish dish : dishList){
    		String hql = "from #Dish where id in (select childId from #DishParent where parentId = ?)";
    		hql = hql.replaceAll("#Dish", Dish.class.getName());
    		hql = hql.replaceAll("#DishParent", DishParent.class.getName());
    		List<Dish> children = theDao.getHibernateTemplate().find(hql, new Object[]{dish.getId()});
    		dish.setChildren(children);
    		if(!dishMap.containsKey(dish.getId())){
    			dishMap.put(dish.getId(), dish);
    		}
    		for(Dish child : children){
    			if(!dishMap.containsKey(child.getId())){
        			dishMap.put(child.getId(), child);
        		}
    		}
    	}
    	return dishMap;
    }
    
    public void parseGroupMemberCount(List<Group> groupList){
    	for(Group group : groupList){
    		long count = theDao.selectCount(UserGroup.class, new String[]{"groupId"}, new Object[]{group.getId()});
    		group.setMemberCount(count);
    	}
    }
    
    public void parseRestaurantDishCount(List<Restaurant> restaurantList) {
    	for(Restaurant restaurant : restaurantList){
    		long count = theDao.selectCount(Dish.class, new String[]{"restaurantId"}, new Object[]{restaurant.getId()});
    		restaurant.setDishCount(count);
    	}
	}
    
    /**
     * 
     * @param provideGroupId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<OrderProvider> getOrderProviderList(int start, int size, String userId){
    	String hql = "from #OrderProvider where id in (select orderProviderId from #GroupProvider where groupId in (select groupId from #UserGroup where userId = ?)) order by createDate desc";
    	hql = hql.replaceAll("#OrderProvider", OrderProvider.class.getName());
    	hql = hql.replaceAll("#GroupProvider", GroupProvider.class.getName());
    	hql = hql.replaceAll("#UserGroup", UserGroup.class.getName());
    	return theDao.getHibernateTemplate().find(hql, new Object[]{userId});
    }
    
    @SuppressWarnings("unchecked")
	public List<Dish> getDishList(int start, int size, String restaurantId){
    	DetachedCriteria c = DetachedCriteria.forClass(Dish.class);
    	if(StringUtils.isEmpty(restaurantId)){
    		return Collections.EMPTY_LIST;
    	}else{
    		c.add(Restrictions.or(Restrictions.eq("restaurantId", restaurantId), Restrictions.isNull("restaurantId")));
    	}
    	return theDao.getHibernateTemplate().findByCriteria(c);
    }

	public void deleteUserGroup(String userId, String groupId) {
		UserGroup userGroup = theDao.findOneByFields(UserGroup.class, new String[]{"userId", "groupId"}, new Object[]{userId, groupId});
		if(userGroup != null){
			theDao.getHibernateTemplate().delete(userGroup);
		}
	}
    
	@SuppressWarnings("unchecked")
	public List<User> getGroupMemberList(String groupId, String ownerId){
		String hql = "from #User where id <> ? and id in (select userId from #UserGroup where groupId = ?)";
		hql = hql.replaceAll("#User", User.class.getName());
    	hql = hql.replaceAll("#UserGroup", UserGroup.class.getName());
		return theDao.getHibernateTemplate().find(hql, new Object[]{ownerId, groupId});
	}
	
	@SuppressWarnings("unchecked")
	public List<Restaurant> getProvideRestaurant(String orderProviderId){
		OrderProvider orderProvider = theDao.getHibernateTemplate().get(OrderProvider.class, orderProviderId);
		if(orderProvider == null){
			return Collections.EMPTY_LIST;
		}
		String restaurants = orderProvider.getRestaurants();
		restaurants = restaurants.replaceAll(",", "','");
		String hql = "from #Restaurant where id in ('" + restaurants + "')";
		hql = hql.replaceAll("#Restaurant", Restaurant.class.getName());
		return theDao.getHibernateTemplate().find(hql);
	}
	
	@SuppressWarnings("unchecked")
	public List<Order> getOrderList(String providerId, String userId){
		DetachedCriteria c = DetachedCriteria.forClass(Order.class);
		c.add(Restrictions.eq("providerId", providerId));
		if(userId != null){
			c.add(Restrictions.eq("userId", userId));
		}
		return theDao.getHibernateTemplate().findByCriteria(c);
	}
	
	public void parseOrderList(List<Order> orderList){
		//cache
		for(Order order : orderList){
			String userId = order.getUserId();
			String dishId = order.getDishId();
			User user = theDao.getHibernateTemplate().get(User.class, userId);
			Dish dish = theDao.getHibernateTemplate().get(Dish.class, dishId);
			if(user != null){
				order.setUserName(user.getNickname());
			}
			if(dish != null){
				order.setDishName(dish.getName());
				order.setDishPrice(dish.getPrice());
				Restaurant restaurant = theDao.getHibernateTemplate().get(Restaurant.class, dish.getRestaurantId());
				if(restaurant != null){
					order.setRestaurantId(restaurant.getId());
					order.setRestaurantName(restaurant.getName());
				}
			}
		}
	}
	
	public void cancelProvide(String orderProviderId){
		String deleteOrder = "delete from #Order where providerId = ?";
		deleteOrder = deleteOrder.replaceAll("#Order", Order.class.getName());
		theDao.getHibernateTemplate().bulkUpdate(deleteOrder, new Object[]{orderProviderId});
		
		String deleteGroupProvider = "delete from #GroupProvider where orderProviderId = ?";
		deleteGroupProvider = deleteGroupProvider.replaceAll("#GroupProvider", GroupProvider.class.getName());
		theDao.getHibernateTemplate().bulkUpdate(deleteGroupProvider, new Object[]{orderProviderId});
		
		delete(OrderProvider.class, orderProviderId);
	}

	/**
	 * copy a file from storage
	 * @param path storage path
	 * @param oriFileName
	 * @param newFileName
	 * @return success or not
	 */
	public boolean copyFile(String path, String oriFileName, String newFileName) {
		byte[] oriFile = storage.read(path, oriFileName);
		if (oriFile == null) {
			return false;
		}
		return storage.write(path, newFileName, oriFile);
	}
	
}
