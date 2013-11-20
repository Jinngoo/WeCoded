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
import com.jinva.bean.datamodel.OrderForm;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.Restaurant;
import com.jinva.bean.datamodel.Team;
import com.jinva.bean.datamodel.TeamProvider;
import com.jinva.bean.datamodel.User;
import com.jinva.bean.datamodel.UserTeam;
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
	    if(id == null){
	        return null;
	    }
		User user = theDao.getHibernateTemplate().get(User.class, id);
		if(user != null){
			return user.getNickname();
		}else{
			return null;
		}
	}
	
	public long getTeamMemberCount(String teamId){
		return theDao.selectCount(UserTeam.class, new String[]{"teamId"}, new Object[]{teamId});
	}
	
	
    /**
     * 
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<Team> getTeamList(int start, int size){
    	List<Team> result = (List<Team>) theDao.find(Team.class);
    	Map<String, String> cache = new HashMap<String, String>();
		parseTeamOwnerName(result, cache);
    	if(size == -1){
    		return result;
    	}else{
    		return (List<Team>) CommonUtil.fillBlankList(result, Team.class, size);
    	}
    }
    
    @SuppressWarnings("unchecked")
	public List<Team> getMyTeamList(int start, int size, String userId){
    	String hql = "from #Team where ownerId = ?";
    	hql = hql.replaceAll("#Team", Team.class.getName());
    	return theDao.getHibernateTemplate().find(hql, new Object[]{userId});
    }
    
    @SuppressWarnings("unchecked")
	public List<Team> getJoinedTeamList(int start, int size, String userId){
    	String hql = "from #Team where ownerId <> ? and id in (select teamId from #UserTeam where userId = ?)";
    	hql = hql.replaceAll("#Team", Team.class.getName());
    	hql = hql.replaceAll("#UserTeam", UserTeam.class.getName());
    	return theDao.getHibernateTemplate().find(hql, new Object[]{userId, userId});
    }
    
    @SuppressWarnings("unchecked")
	public List<Team> getOtherTeamList(int start, int size, String userId){
    	String hql = "from #Team where ownerId <> ? and id not in (select teamId from #UserTeam where userId = ?)";
    	hql = hql.replaceAll("#Team", Team.class.getName());
    	hql = hql.replaceAll("#UserTeam", UserTeam.class.getName());
    	return theDao.getHibernateTemplate().find(hql, new Object[]{userId, userId});
    }
    
    /**
     * 获得可选参加订餐的小组，包含我创建和我参加
     * @param start
     * @param size
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Team> getOptionalTeamList(int start, int size, String userId){
        String hql = "from #Team where id in (select teamId from #UserTeam where userId = ?)";
        hql = hql.replaceAll("#Team", Team.class.getName());
        hql = hql.replaceAll("#UserTeam", UserTeam.class.getName());
        return theDao.getHibernateTemplate().find(hql, new Object[]{userId});
    }
    
    /**
     * 获得可选为订餐的餐馆，包含我创建和公共的
     * @param start
     * @param size
     * @param userId
     * @return
     */
    @SuppressWarnings("unchecked")
    public List<Restaurant> getOptionalRestaurantList(int start, int size, String userId){
        String hql = "from #Restaurant where ownerId = ? or belong = ?";
        hql = hql.replaceAll("#Restaurant", Restaurant.class.getName());
        List<Restaurant> restaurantList = theDao.getHibernateTemplate().find(hql, new Object[]{userId, Restaurant.BELONG_PUBLIC});
        this.parseRestaurantOwnerName(restaurantList, new HashMap<String, String>());
        this.parseRestaurantDishCount(restaurantList);
        return restaurantList;
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
    public List<Restaurant> getPublicRestaurantList(int start, int size, String userId){
    	String hql = "from #Restaurant where belong = ?";
    	hql = hql.replaceAll("#Restaurant", Restaurant.class.getName());
    	List<Restaurant> myRestaurantList = theDao.getHibernateTemplate().find(hql, new Object[]{Restaurant.BELONG_PUBLIC});
    	this.parseRestaurantOwnerName(myRestaurantList, new HashMap<String, String>());
    	this.parseRestaurantDishCount(myRestaurantList);
    	return myRestaurantList;
    }
    
    @SuppressWarnings("unchecked")
    public List<Restaurant> getOtherRestaurantList(int start, int size, String userId){
        String hql = "from #Restaurant where ownerId <> ? and belong = ?";
        hql = hql.replaceAll("#Restaurant", Restaurant.class.getName());
        List<Restaurant> myRestaurantList = theDao.getHibernateTemplate().find(hql, new Object[]{userId, Restaurant.BELONG_PRIVATE});
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
    
    public void parseTeamOwnerName(List<Team> TeamList, Map<String, String> cache){
    	if(cache == null){
    		cache = new HashMap<String, String>();
    	}
    	for(Team Team : TeamList){
    		String ownerId = Team.getOwnerId();
    		if(StringUtils.isEmpty(ownerId)){
    			continue;
    		}
    		if(cache.containsKey(ownerId)){
    			Team.setOwnerName(cache.get(ownerId));
    		}else{
    			User user = theDao.getHibernateTemplate().get(User.class, ownerId);
    			if(user != null){
    				String ownerName = user.getNickname();
    				cache.put(ownerId, ownerName);
    				Team.setOwnerName(ownerName);
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
	public void parseOrderProviderTeam(List<OrderProvider> orderProviderList){
    	for(OrderProvider orderProvider : orderProviderList){
    		String hql = "select name from #Team where id in (select teamId from #TeamProvider where orderProviderId = ?)";
    		hql = hql.replaceAll("#Team", Team.class.getName());
    		hql = hql.replaceAll("#TeamProvider", TeamProvider.class.getName());
    		List<String> TeamNameList = theDao.getHibernateTemplate().find(hql, new Object[]{orderProvider.getId()});
    		orderProvider.setReceiveTeams(StringUtils.join(TeamNameList, ","));
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
    
    public void parseTeamMemberCount(List<Team> TeamList){
    	for(Team Team : TeamList){
    		long count = theDao.selectCount(UserTeam.class, new String[]{"teamId"}, new Object[]{Team.getId()});
    		Team.setMemberCount(count);
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
     * @param provideteamId
     * @return
     */
    @SuppressWarnings("unchecked")
	public List<OrderProvider> getOrderProviderList(int start, int size, String userId){
    	String hql = "from #OrderProvider where id in (select orderProviderId from #TeamProvider where teamId in (select teamId from #UserTeam where userId = ?)) order by createDate desc";
    	hql = hql.replaceAll("#OrderProvider", OrderProvider.class.getName());
    	hql = hql.replaceAll("#TeamProvider", TeamProvider.class.getName());
    	hql = hql.replaceAll("#UserTeam", UserTeam.class.getName());
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

	public void deleteUserTeam(String userId, String teamId) {
		UserTeam userTeam = theDao.findOneByFields(UserTeam.class, new String[]{"userId", "teamId"}, new Object[]{userId, teamId});
		if(userTeam != null){
			theDao.getHibernateTemplate().delete(userTeam);
		}
	}
    
	@SuppressWarnings("unchecked")
	public List<User> getTeamMemberList(String teamId, String ownerId){
		String hql = "from #User where id in (select userId from #UserTeam where userId <> ? and teamId = ?)";
		hql = hql.replaceAll("#User", User.class.getName());
    	hql = hql.replaceAll("#UserTeam", UserTeam.class.getName());
		return theDao.getHibernateTemplate().find(hql, new Object[]{ownerId, teamId});
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
	public List<OrderForm> getOrderList(String providerId, String userId){
		DetachedCriteria c = DetachedCriteria.forClass(OrderForm.class);
		c.add(Restrictions.eq("providerId", providerId));
		if(userId != null){
			c.add(Restrictions.eq("userId", userId));
		}
		return theDao.getHibernateTemplate().findByCriteria(c);
	}
	
	public void parseOrderList(List<OrderForm> orderList){
		//cache
		for(OrderForm order : orderList){
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
		String deleteOrder = "delete from #OrderForm where providerId = ?";
		deleteOrder = deleteOrder.replaceAll("#OrderForm", OrderForm.class.getName());
		theDao.getHibernateTemplate().bulkUpdate(deleteOrder, new Object[]{orderProviderId});
		
		String deleteTeamProvider = "delete from #TeamProvider where orderProviderId = ?";
		deleteTeamProvider = deleteTeamProvider.replaceAll("#TeamProvider", TeamProvider.class.getName());
		theDao.getHibernateTemplate().bulkUpdate(deleteTeamProvider, new Object[]{orderProviderId});
		
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
