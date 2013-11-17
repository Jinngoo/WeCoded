package com.jinva.util;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;

import com.jinva.consts.JinvaConsts;

//import com.sina.sae.storage.SaeStorage;
//import com.sina.sae.util.SaeUserInfo;

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

//	private static final String APP_NAME = "jinva";
//	private static final String DOMAIN_NAME = "jinva";
//	
//	public static boolean write(String name, String suffix, byte[] content){
//		String tmpdir = System.getProperty("java.io.tmpdir");
//		System.setProperty("java.io.tmpdir", "c:/jinva_storage");
//		SaeStorage storage = new SaeStorage(SaeUserInfo.getAccessKey(), SaeUserInfo.getSecretKey(), APP_NAME);
//		System.setProperty("java.io.tmpdir", tmpdir);
//		return storage.write(DOMAIN_NAME, name + "." + suffix, content);
//	}
//	
//	public static byte[] read(String name, String suffix){
//		String tmpdir = System.getProperty("java.io.tmpdir");
//		System.setProperty("java.io.tmpdir", "c:/jinva_storage");
//		SaeStorage storage = new SaeStorage(SaeUserInfo.getAccessKey(), SaeUserInfo.getSecretKey(), APP_NAME);
//		System.setProperty("java.io.tmpdir", tmpdir);
//		return storage.read(APP_NAME, name + "." + suffix);
//	}
	
}
