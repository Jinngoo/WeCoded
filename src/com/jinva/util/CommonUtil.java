package com.jinva.util;

import java.util.ArrayList;
import java.util.List;

public class CommonUtil {

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
