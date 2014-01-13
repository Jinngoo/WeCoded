package com.jinva.support.cache;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinva.bean.datamodel.User;
import com.jinva.dao.base.BaseDao;

@Component
public class UserCache {

    @Autowired
    private BaseDao baseDao;

    private static Map<String, User> cache;
    
    private void init() {
        if (cache == null) {
            reload();
        }
    }

    private void reload() {
        List<User> userList = baseDao.select(User.class);
        cache = Collections.synchronizedMap(new HashMap<String, User>(userList.size()));
        for (User user : userList) {
            cache.put(user.getId(), user);
        }
        userList = null;
    }

    public User get(String id) {
        init();
        return cache.get(id);
    }

    public String getName(String id) {
        User user = get(id);
        return user == null ? null : user.getName();
    }

    public String getNickname(String id) {
        User user = get(id);
        return user == null ? null : user.getNickname();
    }
    
    public void reloadUser(String id) {
        User user = baseDao.get(User.class, id);
        if (user != null) {
            cache.remove(id);
            cache.put(id, user);
        }
    }

}
