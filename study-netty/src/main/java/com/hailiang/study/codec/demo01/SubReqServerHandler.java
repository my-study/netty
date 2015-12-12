package com.hailiang.study.codec.demo01;

import com.hailiang.study.codec.demo01.protobuf.SubscribeReqProto;
import com.hailiang.study.codec.demo01.protobuf.SubscribeRespProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqServerHandler extends ChannelHandlerAdapter {

	/**
	 * 由于ProtoBufDecoder已经对消息进行了自动解码，因此接收到的订购请求消息可以直接使用
	 */
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		SubscribeReqProto.SubscribeReq req = (SubscribeReqProto.SubscribeReq) msg;
		if ("hailiang.jiang".equalsIgnoreCase(req.getUsername())) {
			System.out.println("Service accept client subscribe req : [" + req.toString() + "]");
			ctx.writeAndFlush(resp(req.getSubReqId()));
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause)	throws Exception {
		cause.printStackTrace();
		ctx.close(); //发生异常，关闭链路
	}

	private SubscribeRespProto.SubscribeResp resp(int subReqId) {
		SubscribeRespProto.SubscribeResp.Builder builder = SubscribeRespProto.SubscribeResp.newBuilder();
		builder.setSubReqId(subReqId);
		builder.setRespCode(0);
		builder.setDesc("Netty book order succeed, 3 days later, send to then designated address");
		return builder.build();
	}
	
}
