package com.hailiang.study.serializable.demo01;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import com.hailiang.study.serializable.demo01.model.SubscribeReq;

public class SubReqClientHandler extends ChannelHandlerAdapter {
	
	public SubReqClientHandler() {}

	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.write(subReq(i));
		}
		ctx.flush();
	}
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println("Receive server response : [" + msg + "]");
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

	private SubscribeReq subReq(int i) {
		SubscribeReq req = new SubscribeReq();
		
		req.setAddress("上海市普陀区");
		req.setPhoneNumber("155****8516");
		req.setProductName("Netty权威指南");
		req.setSubReqId(i);
		req.setUsername("hailiang.jiang");
		
		return req;
	}
	
}
