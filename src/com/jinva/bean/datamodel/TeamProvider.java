package com.jinva.bean.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "TeamProvider")
public class TeamProvider {

	private String id;

	private String teamId;

	private String orderProviderId;

	@GenericGenerator(name = "generator", strategy = "org.hibernate.id.UUIDGenerator")
	@Id
	@GeneratedValue(generator = "generator")
	@Column(name = "id", length = 36, unique = true, nullable = false)
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	@Column(name = "teamId", length = 36, nullable = false)
	public String getTeamId() {
		return teamId;
	}

	public void setTeamId(String teamId) {
		this.teamId = teamId;
	}

	@Column(name = "orderProviderId", length = 36, nullable = false)
	public String getOrderProviderId() {
		return orderProviderId;
	}

	public void setOrderProviderId(String orderProviderId) {
		this.orderProviderId = orderProviderId;
	}

}
