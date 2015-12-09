package com.hailiang.study.netty.demo06;

import java.io.Serializable;
import java.nio.ByteBuffer;

/**
 * Entry-用户
 * 
 * @classname com.hailiang.study.netty.demo06.User
 * @description TODO
 * @date 2015年12月9日 下午1:55:22
 * @version: v1.0.0
 */
public class User implements Serializable {
	private static final long serialVersionUID = 1L;

	private int userId;
	private String username;
	
	public User buildUsername(String username) {
		this.username = username;
		return this;
	}
	
	public User buildUserId(int userId) {
		this.userId = userId;
		return this;
	}

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
	
	/**
	 * 基于ByteBuffer通用的二进制编码技术，对User对象进行编码，编码结果仍然是byte数组
	 * @methodName com.hailiang.study.netty.demo06.User.codec
	 * @author hailiang.jiang
	 * @date 2015年12月9日 下午1:58:26
	 * @version: v1.0.0
	 */
	public byte[] codec() {
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		
		byte[] value = this.username.getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(this.userId);
		
		buffer.flip();
		
		value = null;
		
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);
		
		return result;
	}
	
	
	public byte[] codec(ByteBuffer buffer) {
		buffer.clear();
		
		byte[] value = this.username.getBytes();
		buffer.putInt(value.length);
		buffer.put(value);
		buffer.putInt(this.userId);
		
		buffer.flip();
		
		value = null;
		
		byte[] result = new byte[buffer.remaining()];
		buffer.get(result);
		
		return result;
	}

}
