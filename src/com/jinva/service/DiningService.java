package com.jinva.service;

import org.hibernate.criterion.Criterion;
import org.hibernate.criterion.DetachedCriteria;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Property;
import org.springframework.stereotype.Service;

import com.jinva.bean.datamodel.OrderProvider;
import com.jinva.bean.datamodel.TeamProvider;
import com.jinva.bean.datamodel.UserTeam;
import com.jinva.service.base.BaseService;
import com.jinva.support.page.Page;

@Service
public class DiningService extends BaseService {

    public void test(String userId) {
        long start = System.currentTimeMillis();
        
        //方法1
        String hql = "from #OrderProvider where id in (select orderProviderId from #TeamProvider where teamId in (select teamId from #UserTeam where userId = ?)) order by createDate desc";
        hql = hql.replaceAll("#OrderProvider", OrderProvider.class.getName());
        hql = hql.replaceAll("#TeamProvider", TeamProvider.class.getName());
        hql = hql.replaceAll("#UserTeam", UserTeam.class.getName());
        Page<OrderProvider> page1 = pageDao.selectPage(hql, new Object[]{userId}, 2, 5);
        System.out.println(page1.getData().size());
        
        System.out.println(System.currentTimeMillis()-start+"ms");
        start = System.currentTimeMillis();
        
        //方法2
        DetachedCriteria userTeamSubSelect = DetachedCriteria.forClass(UserTeam.class);
        userTeamSubSelect.add(Property.forName("userId").eq(userId));
        userTeamSubSelect.setProjection(Property.forName("teamId"));
        
        DetachedCriteria teamProviderSubSelect = DetachedCriteria.forClass(TeamProvider.class);
        teamProviderSubSelect.add(Property.forName("teamId").in(userTeamSubSelect));
        teamProviderSubSelect.setProjection(Property.forName("orderProviderId"));
        
        Criterion orderProviderSelect = Property.forName("id").in(teamProviderSubSelect);
        
        Order order = Order.desc("createDate");
        
        Page<OrderProvider> page2 = pageDao.selectPage(OrderProvider.class, orderProviderSelect, order, 2, 5);
        System.out.println(page2.getData().size());
        
        System.out.println(System.currentTimeMillis()-start+"ms");
    }
    
}
