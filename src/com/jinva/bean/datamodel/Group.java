package com.jinva.bean.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "Group")
public class Group {

	private String id;

	private String name;

	private String password;

	private String ownerId;

	private Integer level;

	private String introduction;

	/////////////////////////////
	//非数据库字段
	private String ownerName;
	
	private long memberCount;

	public Group() {
		super();
		// TODO Auto-generated constructor stub
	}

	public Group(String name, String ownerId, String introduction) {
		super();
		this.name = name;
		this.ownerId = ownerId;
		this.introduction = introduction;
	}

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

	@Column(name = "name", length = 32, nullable = false)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "password", length = 300)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "ownerId", length = 32, nullable = false)
	public String getOwnerId() {
		return ownerId;
	}

	public void setOwnerId(String ownerId) {
		this.ownerId = ownerId;
	}

	@Column(name = "level")
	public Integer getLevel() {
		return level;
	}

	public void setLevel(Integer level) {
		this.level = level;
	}

	@Column(name = "introduction", length = 300)
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
	public long getMemberCount() {
		return memberCount;
	}

	public void setMemberCount(long memberCount) {
		this.memberCount = memberCount;
	}

}
