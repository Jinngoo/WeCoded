package com.jinva.bean.datamodel;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Restaurant", catalog = "app_jinva")
public class Restaurant {

	private String id;

	private String name;

	private String ownerId;

	private String address;

	private String telphone;

	private String introduction;

	/////////////////////////////
	//非数据库字段
	private String ownerName;
	private Long dishCount;

	private List<String> telphoneList;

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

	@Column(name = "name", length = 300)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "ownerId", length = 32, nullable = false)
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "address", length = 600)
	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Column(name = "telphone", length = 600)
	public String getTelphone() {
		return telphone;
	}

	public void setTelphone(String telphone) {
		this.telphone = telphone;
	}

	@Column(name = "introduction", length = 600)
	public String getIntroduction() {
		return introduction;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	@Transient
	public String getOwnerName() {
		return ownerName;
	}

	public void setOwnerName(String ownerName) {
		this.ownerName = ownerName;
	}

	@Transient
	public List<String> getTelphoneList() {
		return telphoneList;
	}

	public void setTelphoneList(List<String> telphoneList) {
		this.telphoneList = telphoneList;
	}

	@Transient
	public Long getDishCount() {
		return dishCount;
	}

	public void setDishCount(Long dishCount) {
		this.dishCount = dishCount;
	}

}
