package com.hailiang.study.netty.demo04;

import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class EchoClientHandler extends ChannelHandlerAdapter {

	private int counter = 0;
	
	private static final String ECHO_REQ = "Hi hailiang.jiang, Welcome to Netty." + NettyConstant.DELIMITER;
	
	public EchoClientHandler() {
	}
	
	/**
	 * 在TCP链路建立成功之后，循环发送请求消息给服务端
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		for (int i = 0; i < 10; i++) {
			ctx.writeAndFlush(Unpooled.copiedBuffer(ECHO_REQ.getBytes()));
		}
	}
	
	/**
	 * 打印接收到的服务端应答消息，并同时计数
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		String body = (String) msg;
		System.out.println("This is " + (++counter) + " times receive server : [" + body + "]");
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
	
}
