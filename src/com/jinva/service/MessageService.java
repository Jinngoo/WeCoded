package com.jinva.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.jinva.bean.datamodel.TeamProvider;
import com.jinva.bean.datamodel.Message;
import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.UserTeam;
import com.jinva.dao.TheDao;

@Component
public class MessageService {

	@Autowired
	private TheDao theDao;
	
	@SuppressWarnings("unchecked")
	public long sendMessage(OrderProvider orderProvider, String[] exceptReceiversId){
		List<String> receivers = new ArrayList<String>();
		
		String hql = "select distinct userId from #UserTeam where TeamId in (select TeamId from #TeamProvider where orderProviderId = ?)";
		hql = hql.replaceAll("#UserTeam", UserTeam.class.getName());
		hql = hql.replaceAll("#TeamProvider", TeamProvider.class.getName());
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
