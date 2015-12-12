package com.hailiang.study.codec.demo02.model;

import java.io.Serializable;

/**
 * 客户端请求实体
 */
public class User implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;
	private String username;
	private String password;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "UserRequest [userId=" + userId + ", username=" + username
				+ ", password=" + password + "]";
	}

}
