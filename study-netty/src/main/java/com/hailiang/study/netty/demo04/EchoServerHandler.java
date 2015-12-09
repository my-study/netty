package com.hailiang.study.netty.demo04;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * EchoServer服务端-EchoServerHandler
 * 
 * @classname com.hailiang.study.netty.demo04.EchoServerHandler
 * @author hailiang.jiang
 * @date 2015年12月9日 上午10:12:11
 * @version: v1.0.0
 * @see
 */
public class EchoServerHandler extends ChannelHandlerAdapter {

	private int counter = 0;
	
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// 接收到的消息
		String body = (String) msg;
		// 输出消息
		System.out.println("This is " + (++counter) + " times receive client : [" + body + "]");
		// 创建ByteBuf，并将原始消息重新返回给客户端
		ByteBuf echo = Unpooled.copiedBuffer((body + NettyConstant.DELIMITER).getBytes());
		ctx.writeAndFlush(echo);
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close(); //发生异常时，关闭链路
	}
	
}
