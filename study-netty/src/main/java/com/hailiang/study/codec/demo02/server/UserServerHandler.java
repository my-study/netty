package com.hailiang.study.codec.demo02.server;

import com.hailiang.study.codec.demo02.model.User;
import com.hailiang.study.codec.demo02.model.UserResponse;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class UserServerHandler extends ChannelHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		User ur = (User) msg;
		System.out.println("客户端请求的数据：" + ur);
		
		ctx.writeAndFlush(response(ur));
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	private UserResponse response(User ur) {
		UserResponse resp = new UserResponse();
		resp.setUserId(ur.getUserId());
		resp.setDesc("响应消息");
		return resp;
	}
	
}
