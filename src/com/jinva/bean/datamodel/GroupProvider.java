package com.jinva.bean.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "GroupProvider")
public class GroupProvider {

	private String id;

	private String groupId;

	private String orderProviderId;

	@GenericGenerator(name = "generator", strategy = "uuid")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", length = 32, unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "groupId", length = 32, nullable = false)
	public String getGroupId() {
		return groupId;
	}

	public void setGroupId(String groupId) {
		this.groupId = groupId;
	}

	@Column(name = "orderProviderId", length = 32, nullable = false)
	public String getOrderProviderId() {
		return orderProviderId;
	}

	public void setOrderProviderId(String orderProviderId) {
		this.orderProviderId = orderProviderId;
	}

}
