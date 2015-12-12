package com.hailiang.study.codec.demo01;

import java.util.ArrayList;
import java.util.List;

import com.hailiang.study.codec.demo01.protobuf.SubscribeReqProto;

import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

public class SubReqClientHandler extends ChannelHandlerAdapter {

	public SubReqClientHandler() {
	}
	
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

	private SubscribeReqProto.SubscribeReq subReq(int i) {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqId(i);
		builder.setUsername("hailiang.jiang");
		builder.setProductName("Netty Book For Protobuf");
		List<String> addresses = new ArrayList<>();
		addresses.add("ShangHai JiangHaiLiang");
		addresses.add("KunShan TangYuanShang");
		addresses.add("ZhangShan WangLi");
		builder.addAllAddress(addresses);
		return builder.build();
	}
	
}
