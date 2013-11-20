package com.jinva.service;

import java.io.InputStream;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.Charsets;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Component;

import com.jinva.bean.datamodel.Dish;
import com.jinva.bean.datamodel.Restaurant;
import com.jinva.bean.datamodel.Team;
import com.jinva.bean.datamodel.User;
import com.jinva.bean.datamodel.UserTeam;

@Component
public class InitSqlDataService extends JinvaService{

    private static final String ADMIN_ID = "9c5132c6-7a69-4746-9ada-55d8c1d3ce2e";
    
    public void initSqlData(){
        clearAllData();
        
        String defaultTeamId = initDefaultTeam();
        initUser(defaultTeamId);
        
        Map<String, String> idMap = initRestaurant();
        initDish(idMap);
    }
    
    private void clearAllData(){
        //TODO 删除相关图片
        bulkUpdate("delete from " + UserTeam.class.getName());
        bulkUpdate("delete from " + Team.class.getName());
        bulkUpdate("delete from " + Dish.class.getName());
        bulkUpdate("delete from " + Restaurant.class.getName());
        bulkUpdate("delete from " + User.class.getName() + " where id <> ?", new Object[]{ADMIN_ID});
    }
    
    private String initDefaultTeam(){
        Team team = new Team();
        team.setId(null);
        team.setName("默认分组");
        team.setIntroduction("大家都在一起");
        team.setOwnerId(ADMIN_ID);
       
        String teamId = (String) save(team);
        
        UserTeam userTeam = new UserTeam();
        userTeam.setTeamId(teamId);
        userTeam.setUserId(ADMIN_ID);
        save(userTeam);
        return teamId;
    }
    
    @SuppressWarnings("unchecked")
    private void initUser(String defaultTeamId){
        String defaultPassword = DigestUtils.md5Hex("12345");
        SAXReader reader = new SAXReader();
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("xml/user.xml");
            Document dom = reader.read(in);
            Element root = dom.getRootElement();
            List<Element> rows = root.element("rowset").elements("row");
            for(Element row : rows){
                String nickname = null;
                String loginname = null;
                List<Element> columns = row.elements();
                for(Element column : columns){
                    String field = column.attributeValue("name");
                    String value = column.getText();
                    if (value != null) {
                        value = new String(value.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8);
                    }
                    if ("C_LoginID".equals(field)) {
                        loginname = value;
                    } else if ("C_Name".equals(field)) {
                        nickname = value;
                    }
                }
                User user = new User();
                user.setId(null);
                user.setName(loginname);
                user.setNickname(nickname);
                user.setPassword(defaultPassword);
                save(user);
                
                UserTeam userTeam = new UserTeam();
                userTeam.setId(null);
                userTeam.setEnterDate(new Date());
                userTeam.setTeamId(defaultTeamId);
                userTeam.setUserId(user.getId());
                save(userTeam);
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    
    @SuppressWarnings("unchecked")
    private Map<String, String> initRestaurant(){
        Map<String, String> idMap = new HashMap<String, String>();
        SAXReader reader = new SAXReader();
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("xml/restaurant.xml");
            Document dom = reader.read(in);
            Element root = dom.getRootElement();
            List<Element> rows = root.element("rowset").elements("row");
            for(Element row : rows){
                String name = null;
                String telphone = null;
                String address = null;
                String id = null;
                List<Element> columns = row.elements();
                for(Element column : columns){
                    String field = column.attributeValue("name");
                    String value = column.getText();
                    if (value != null) {
                        value = new String(value.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8);
                    }
                    if ("C_MC".equals(field)) {
                        name = value;
                    } else if ("C_ADDRESS".equals(field)) {
                        address = value;
                    } else if ("C_BH".equals(field)) {
                        id = value;
                    }
                }
                if(id != null && name != null){
                    Restaurant restaurant = new Restaurant();
                    restaurant.setId(null);
                    restaurant.setName(name);
                    restaurant.setTelphone(telphone);
                    restaurant.setAddress(address);
                    restaurant.setBelong(Restaurant.BELONG_PUBLIC);
                    restaurant.setIntroduction(null);
                    save(restaurant);
                    idMap.put(id, restaurant.getId());
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return idMap; 
    }
    
//    /**
//     * 9c5132c6-7a69-4746-9ada-55d8c1d3ce2e
//     * @param uuid
//     * @return
//     */
//    private String convertUUID(String uuid){
//        if(uuid.length() == 32){
//            StringBuilder buff = new StringBuilder(36);
//            buff.append(uuid.substring(0, 8)).append("-");
//            buff.append(uuid.substring(8, 12)).append("-");
//            buff.append(uuid.substring(12, 16)).append("-");
//            buff.append(uuid.substring(16, 20)).append("-");
//            buff.append(uuid.substring(20, 32));
//            return buff.toString();
//        }
//        return uuid;
//    }
    
    @SuppressWarnings("unchecked")
    private void initDish(Map<String, String> idMap){
        SAXReader reader = new SAXReader();
        try {
            InputStream in = this.getClass().getClassLoader().getResourceAsStream("xml/dish.xml");
            Document dom = reader.read(in);
            Element root = dom.getRootElement();
            List<Element> rows = root.element("rowset").elements("row");
            for(Element row : rows){
                String name = null;
                Double price = null;
                String pId = null;
                List<Element> columns = row.elements();
                for(Element column : columns){
                    String field = column.attributeValue("name");
                    String value = column.getText();
                    if (value != null) {
                        value = new String(value.getBytes(Charsets.ISO_8859_1), Charsets.UTF_8);
                    }
                    if ("C_MC".equals(field)) {
                        name = value;
                    } else if ("C_BH_CG".equals(field)) {
                        pId = value;
                    }else if ("N_PRICE".equals(field)) {
                        price = field == null ? Double.valueOf(0) : Double.valueOf(value);
                    }
                }
                if(pId != null && name != null && idMap.containsKey(pId)){
                    Dish dish = new Dish();
                    dish.setId(null);
                    dish.setName(name);
                    dish.setPrice(price);
                    dish.setRestaurantId(idMap.get(pId));
                    save(dish);
                }
            }
        } catch (DocumentException e) {
            e.printStackTrace();
        }
    }
    
}
