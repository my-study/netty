package com.hailiang.study.netty.demo01;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import java.util.logging.Logger;

/**
 * Netty时间服务器客户端-TimeClientHandler
 * 
 * @classname com.hailiang.study.netty.demo01.TimeClientHandler
 * @description TODO
 * @author hailiang.jiang
 * @date 2015年12月4日 下午3:09:51
 * @version: v1.0.0
 * @see
 */
public class TimeClientHandler extends ChannelHandlerAdapter {
	private static final Logger logger = Logger.getLogger(TimeClientHandler.class.getName());
	private final ByteBuf firstMessage;
	
	public TimeClientHandler() {
		byte[] req = "QUERY TIME ORDER".getBytes();
		firstMessage = Unpooled.buffer(req.length);
		firstMessage.writeBytes(req);
	}

	/**
	 * 当客户单和服务器端TCP链路建立成功之后，Netty的NIO线程会调用channelActive方法，并发送查询时间的指令给服务端
	 */
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		ctx.writeAndFlush(firstMessage);
	}

	/**
	 * 当服务器返回应答消息时，channelRead方法被调用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		ByteBuf buf = (ByteBuf) msg;
		byte[] req = new byte[buf.readableBytes()];
		buf.readBytes(req);
		
		String body = new String(req, "UTF-8");
		System.out.println("Now is : " + body);
	}

	/**
	 * 当发生异常时，打印异常日志，释放客户端资源
	 */
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		logger.warning("Unexpected excetpion from downstream: " + cause.getMessage());
		ctx.close(); //释放资源
	}
	
	
}
