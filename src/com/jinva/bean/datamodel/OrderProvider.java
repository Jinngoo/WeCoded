package com.jinva.bean.datamodel;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "OrderProvider")
public class OrderProvider {

	private String id;

	private String provideUserId;

	//
	// private String receiveUsers;

	private Date createDate;

	private String restaurants;

	private Integer status;

	// ///////////////////////////
	// 非数据库字段
	private String provideUserName;
	private String receiveTeams;

	public static final int STATUS_OFFER = 1;
	public static final int STATUS_END = 2;

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

	@Column(name = "provideUserId", length = 36, nullable = false)
	public String getProvideUserId() {
		return provideUserId;
	}

	public void setProvideUserId(String provideUserId) {
		this.provideUserId = provideUserId;
	}

	//
	// @Column(name = "receiveUsers", length = 600)
	// public String getReceiveUsers() {
	// return receiveUsers;
	// }
	//
	// public void setReceiveUsers(String receiveUsers) {
	// this.receiveUsers = receiveUsers;
	// }

	@Column(name = "createDate", length = 300)
	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	@Column(name = "restaurants", length = 600)
	public String getRestaurants() {
		return restaurants;
	}

	public void setRestaurants(String restaurants) {
		this.restaurants = restaurants;
	}

	@Column(name = "status")
	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	@Transient
	public String getProvideUserName() {
		return provideUserName;
	}

	public void setProvideUserName(String provideUserName) {
		this.provideUserName = provideUserName;
	}

	@Transient
	public String getReceiveTeams() {
		return receiveTeams;
	}

	public void setReceiveTeams(String receiveTeams) {
		this.receiveTeams = receiveTeams;
	}
}
