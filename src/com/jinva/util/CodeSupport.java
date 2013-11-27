package com.jinva.util;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.springframework.web.servlet.support.RequestContext;

import com.jinva.bean.datamodel.OrderProvider;

public class CodeSupport {

	private static Map<Integer, String> orderProviderStatusCode = Collections.unmodifiableMap(new HashMap<Integer, String>(){
		private static final long serialVersionUID = 564374620593534207L;
		{
			put(OrderProvider.STATUS_OFFERING, "code.orderProvider.status.offer");
			put(OrderProvider.STATUS_END, "code.orderProvider.status.end");
		}
	});
//	private static Map<Integer, String> messageStatusCode = Collections.unmodifiableMap(new HashMap<Integer, String>(){
//		private static final long serialVersionUID = 564374620593534207L;
//		{
//			put(Message.STATUS_NEW, "code.Message.status.new");
//			put(Message.STATUS_READ, "code.Message.status.read");
//		}
//	});

	public static Map<Serializable, String> getOrderProviderStatusCode(HttpServletRequest request){
        return i18n(orderProviderStatusCode, request);
	}
	
//	public static Map<Serializable, String> getMessageStatusCode(HttpServletRequest request){
//        return i18n(messageStatusCode, request);
//	}
	
    private static Map<Serializable, String> i18n(Map<Integer, String> codeMap, HttpServletRequest request) {
        Map<Serializable, String> result = new HashMap<Serializable, String>(codeMap.size());
        RequestContext requestContext = new RequestContext(request);
        for (Entry<Integer, String> entry : codeMap.entrySet()) {
            String message = requestContext.getMessage(entry.getValue());
            result.put(entry.getKey(), message);
        }
        return result;
    }
	
}
