package com.hailiang.study.serializable.demo01;

import com.hailiang.study.serializable.demo01.model.SubscribeReq;
import com.hailiang.study.serializable.demo01.model.SubscribeResp;

import io.netty.channel.ChannelHandlerAppender;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAppender {
	
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		System.out.println("=======init channelActive========");
	}

	/**
	 * 经过解码器handler ObjectDecoder
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReq req = (SubscribeReq) msg;
		if ("hailiang.jiang".equalsIgnoreCase(req.getUsername())) {
			System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
			ctx.writeAndFlush(resp(req.getSubReqId()));
		}
	}

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close(); // 发生异常，关闭链路
	}
	
	private SubscribeResp resp(int subReqId) {
		SubscribeResp resp = new SubscribeResp();
		resp.setSubReqId(subReqId);
		resp.setRespCode(0);
		resp.setDesc("Netty book order succeed, 3 days later, sent to the designated address.");
		return resp;
	}
	
}
