package com.hailiang.study.netty.demo06;

import java.io.ByteArrayOutputStream;
import java.io.ObjectOutputStream;
import java.nio.ByteBuffer;

import org.junit.Test;

public class UserTest {
	/**
	 * JDK序列化后的码流太大
	 * @methodName com.hailiang.study.netty.demo06.UserTest.testCodec
	 * @author hailiang.jiang
	 * @date 2015年12月9日 下午2:06:59
	 * @version: v1.0.0
	 */
	@Test
	public void testCodec() throws Exception {
		User user = new User();
		user.buildUserId(100).buildUsername("Welcome to Netty");
		
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(baos);
		oos.writeObject(user);
		oos.flush();
		oos.close();
		
		byte[] b = baos.toByteArray();
		System.out.println("the JDK serializable length = " + b.length);
		baos.close();
		
		System.out.println("==========================================");
		System.out.println("the ByteBuffer serializable length = " + user.codec().length);
	}
	
	
	/**
	 * JDK序列化的性能低下
	 * @methodName com.hailiang.study.netty.demo06.UserTest.testPerformCode
	 * @author hailiang.jiang
	 * @date 2015年12月9日 下午2:11:22
	 * @version: v1.0.0
	 */
	@Test
	@SuppressWarnings("unused")
	public void testPerformCode() throws Exception {
		User user = new User();
		user.buildUserId(100).buildUsername("Welcome to Netty");
		
		int loop = 1000000;
		ByteArrayOutputStream baos = null;
		ObjectOutputStream oos = null;
		
		long startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			baos = new ByteArrayOutputStream();
			oos = new ObjectOutputStream(baos);
			oos.writeObject(user);
			oos.flush();
			oos.close();
			
			byte[] b = baos.toByteArray();
			baos.close();
		}
		long endTime = System.currentTimeMillis();
		System.out.println("the JDK serializable cost time is : " + (endTime - startTime) + " ms");
		System.out.println("==========================================");
		
		
		ByteBuffer buffer = ByteBuffer.allocate(1024);
		startTime = System.currentTimeMillis();
		for (int i = 0; i < loop; i++) {
			byte[] b = user.codec(buffer);
		}
		endTime = System.currentTimeMillis();
		
		System.out.println("the ByteBuffer serializable cost time is : " + (endTime - startTime) + " ms");
	}
	
}
