package com.jinva.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletRequest;

public class CommonUtil {

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
