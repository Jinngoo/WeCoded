package com.jinva.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jinva.consts.JinvaConsts;

public class StorageUtil {
    
    private static Map<String, String> TYPE_PATH_MAP = new HashMap<String, String>();
    static{
        TYPE_PATH_MAP.put(JinvaConsts.UPLOAD_TYPE_USER_AVATAR, JinvaConsts.USER_AVATAR_PATH);
        TYPE_PATH_MAP.put(JinvaConsts.UPLOAD_TYPE_GROUP_AVATAR, JinvaConsts.GROUP_AVATAR_PATH);
        TYPE_PATH_MAP.put(JinvaConsts.UPLOAD_TYPE_RESTNURANT_AVATAR, JinvaConsts.RESTAURANT_AVATAR_PATH);
        TYPE_PATH_MAP.put(JinvaConsts.UPLOAD_TYPE_DISH_AVATAR, JinvaConsts.DISH_AVATAR_PATH);
    }
    
    public static String getPathByType(String type){
        if(StringUtils.isBlank(type)){
            return null;
        }
        return TYPE_PATH_MAP.get(type);
    }

}
