package com.jinva.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

public class CommonUtil {

    public static Map<String, Object> getValidParameterMap(HttpServletRequest request, String prefix) {
        Map<String, String[]> params = request.getParameterMap();
        Map<String, Object> validParams = new HashMap<String, Object>();
        for(Entry<String, String[]> entry : params.entrySet()){
            String key = entry.getKey();
            if(key.contains(prefix)){
                validParams.put(key.split("_")[1], entry.getValue());
            }
        }
        return validParams;
    }
    
    public static String toString(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof Object[]) {
            Object[] arr = (Object[]) obj;
            if (arr.length > 0) {
                return (String) arr[0];
            } else {
                return null;
            }
        } else {
            return (String) obj;
        }
    }
    
	/**
     * 
     * @param list
     * @param eleType
     * @param size
     * @return
     */
    public static <T> List<T> fillBlankList(List<T> list, Class<T> eleType, int size){
		if(list == null){
			list = new ArrayList<T>();
		}
		int blankCount = size - list.size();
		try {
			for(int i = 0; i < blankCount; i ++){
				list.add(eleType.newInstance());
			}
		} catch (InstantiationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return list;
	}
    
}
