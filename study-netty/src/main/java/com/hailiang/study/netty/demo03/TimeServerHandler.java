package com.hailiang.study.netty.demo03;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * Netty时间服务器服务端-TimeServerHandler
 * 
 * @classname com.hailiang.study.netty.demo03.TimeServerHandler
 * @description TODO
 * @author hailiang.jiang
 * @date 2015年12月7日 下午6:31:23
 * @version: v1.0.0
 * @see
 */
class TimeServerHandler extends ChannelHandlerAdapter {

	private int counter;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("The time server receive order: " + body + " ; the counter is : " + (++counter));
		
		String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
		currentTime += System.getProperty("line.separator");
		
		ByteBuf resp = Unpooled.copiedBuffer(currentTime.getBytes());
		
		ctx.writeAndFlush(resp);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		ctx.close();
	}
	
}
