package com.jinva.bean.datamodel;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.hibernate.annotations.GenericGenerator;

@Entity
@Table(name = "User")
public class User {

	private String id;

	private String password;

	private String name;

	private String nickname;

	public User() {
	}

	public User(String password, String name, String nickname) {
		this.password = password;
		this.name = name;
		this.nickname = nickname;
	}

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

	@Column(name = "password", length = 300)
	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Column(name = "name", length = 300)
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "nickname", length = 300)
	public String getNickname() {
		return nickname;
	}

	public void setNickname(String nickname) {
		this.nickname = nickname;
	}

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof User) {
            User target = (User) obj;
            return String.valueOf(id).equals(target.getId());
        }
        return false;
    }


}
