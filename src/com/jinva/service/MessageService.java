package com.jinva.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinva.bean.datamodel.GroupProvider;
import com.jinva.bean.datamodel.Message;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.UserGroup;
import com.jinva.dao.TheDao;

@Component
public class MessageService {

	@Autowired
	private TheDao theDao;
	
	@SuppressWarnings("unchecked")
	public long sendMessage(OrderProvider orderProvider, String[] exceptReceiversId){
		List<String> receivers = new ArrayList<String>();
		
		String hql = "select distinct userId from #UserGroup where groupId in (select groupId from #GroupProvider where orderProviderId = ?)";
		hql = hql.replaceAll("#UserGroup", UserGroup.class.getName());
		hql = hql.replaceAll("#GroupProvider", GroupProvider.class.getName());
		List<String> userIdList = theDao.getHibernateTemplate().find(hql, new Object[]{orderProvider.getId()});
		receivers.addAll(userIdList);
		
		if(exceptReceiversId != null){
			receivers.removeAll(Arrays.asList(exceptReceiversId));
		}
		for(String receiverId : receivers){
			Message message = new Message();
			message.setReceiverId(receiverId);
			message.setDate(new Date());
			message.setStatus(Message.STATUS_NEW);
			message.setContent("有新的可参与订餐活动");
			theDao.save(message);
		}
		return receivers.size();
	}
	
}
