package com.hailiang.study.codec.demo01.protobuf;

import java.util.ArrayList;
import java.util.List;

import com.google.protobuf.InvalidProtocolBufferException;
import com.hailiang.study.codec.demo01.protobuf.SubscribeReqProto.SubscribeReq;

public class TestSubscribeReqProto {

	public static void main(String[] args) throws InvalidProtocolBufferException {
		SubscribeReqProto.SubscribeReq req = createSubscribeReq();
		System.out.println("编码前: " + req.toString());
		SubscribeReqProto.SubscribeReq req2 = decode(encode(req));
		System.out.println("解码后: " + req2.toString());
		System.out.println("编码前和编码后，内容对比：" + req.equals(req2));
	}

	private static SubscribeReq decode(byte[] body) throws InvalidProtocolBufferException {
		return SubscribeReqProto.SubscribeReq.parseFrom(body);
	}

	private static byte[] encode(SubscribeReqProto.SubscribeReq req) {
		return req.toByteArray();
	}

	private static SubscribeReqProto.SubscribeReq createSubscribeReq() {
		SubscribeReqProto.SubscribeReq.Builder builder = SubscribeReqProto.SubscribeReq.newBuilder();
		builder.setSubReqId(1);
		builder.setUsername("hailiang.jiang");
		builder.setProductName("Netty Book");
		
		List<String> addresses = new ArrayList<>();
		addresses.add("ShangHai JiangHaiLiang");
		addresses.add("KunShan TangYuanShang");
		addresses.add("ZhangShan WangLi");
		builder.addAllAddress(addresses);
		return builder.build();
	}
	
}
