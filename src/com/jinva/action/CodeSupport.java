package com.jinva.action;

import java.util.HashMap;
import java.util.Map;

import com.jinva.bean.datamodel.Message;
import com.jinva.bean.datamodel.OrderProvider;

public interface CodeSupport {

	Map<Integer, String> orderProviderStatusCode = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 564374620593534207L;
		{
			put(OrderProvider.STATUS_OFFER, "code.orderProvider.status.offer");
			put(OrderProvider.STATUS_END, "code.orderProvider.status.end");
		}
	};
	Map<Integer, String> messageStatusCode = new HashMap<Integer, String>(){
		private static final long serialVersionUID = 564374620593534207L;
		{
			put(Message.STATUS_NEW, "code.orderProvider.status.offer");
			put(Message.STATUS_READ, "code.orderProvider.status.end");
		}
	};

	Map<Integer, String> getOrderProviderStatusCode();
	Map<Integer, String> getMessageStatusCode();
	
}
