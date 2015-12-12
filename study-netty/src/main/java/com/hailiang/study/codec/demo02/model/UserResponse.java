package com.hailiang.study.codec.demo02.model;

import java.io.Serializable;

/**
 * 服务端响应实体
 */
public class UserResponse implements Serializable {

	private static final long serialVersionUID = 1L;

	private int userId;
	private String desc;

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	@Override
	public String toString() {
		return "UserResponse [userId=" + userId + ", desc=" + desc + "]";
	}

}
