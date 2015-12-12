package com.hailiang.study.codec.demo02.client;

import com.hailiang.study.codec.demo02.model.User;
import com.hailiang.study.codec.demo02.model.UserResponse;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class UserClientHandler extends ChannelHandlerAdapter {

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.write(request(i));
		}
		ctx.flush();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		UserResponse response = (UserResponse) msg;
		System.out.println("服务器响应的详细: " + response);	
	}
	
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
		ctx.flush();
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}
	
	private User request(int i) {
		User user = new User();
		user.setUserId(i);
		user.setUsername("hailiang.jiang");
		user.setPassword("jhlishero");
		return user;
	}
	
	
}
