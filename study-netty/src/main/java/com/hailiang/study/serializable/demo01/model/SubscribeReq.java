package com.hailiang.study.serializable.demo01.model;

import java.io.Serializable;

/**
 * 用户订购请求消息
 * 
 * @classname com.hailiang.study.serializable.demo01.SubscribeReq
 * @author hailiang.jiang
 * @date 2015年12月10日 上午11:22:48
 */
public class SubscribeReq implements Serializable {
	/** 默认的序列号ID **/
	private static final long serialVersionUID = 1L;

	/** 订购编号 **/
	private int subReqId;
	
	/** 用户名 **/
	private String username;
	
	/** 订购的产品名称 **/
	private String productName;
	
	/** 订购者电话号码 **/
	private String phoneNumber;
	
	/** 订购者的家庭住址 **/
	private String address;

	public int getSubReqId() {
		return subReqId;
	}

	public void setSubReqId(int subReqId) {
		this.subReqId = subReqId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPhoneNumber(String phoneNumber) {
		this.phoneNumber = phoneNumber;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "SubscribeReq [subReqId=" + subReqId + ", username=" + username
				+ ", productName=" + productName + ", phoneNumber="
				+ phoneNumber + ", address=" + address + "]";
	}

}
