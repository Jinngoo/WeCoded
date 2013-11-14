package com.jinva.bean.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "DishParent")
public class DishParent {

	private String id;

	private String parentId;

	private String childId;

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

	@Column(name = "parentId", length = 36, nullable = false)
	public String getParentId() {
		return parentId;
	}

	public void setParentId(String parentId) {
		this.parentId = parentId;
	}

	@Column(name = "childId", length = 36, nullable = false)
	public String getChildId() {
		return childId;
	}

	public void setChildId(String childId) {
		this.childId = childId;
	}


}
