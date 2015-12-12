package com.hailiang.study.codec.demo02.marshalling;

import io.netty.handler.codec.marshalling.DefaultMarshallerProvider;
import io.netty.handler.codec.marshalling.DefaultUnmarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallerProvider;
import io.netty.handler.codec.marshalling.MarshallingDecoder;
import io.netty.handler.codec.marshalling.MarshallingEncoder;
import io.netty.handler.codec.marshalling.UnmarshallerProvider;

import org.jboss.marshalling.MarshallerFactory;
import org.jboss.marshalling.Marshalling;
import org.jboss.marshalling.MarshallingConfiguration;

/**
 * 通过此类，可以创建MarshallingDecoder解码器、MarshallingEncoder编码器
 * 
 * @classname com.hailiang.study.codec.demo02.MashallingCodeFactory
 * @author hailiang.jiang
 * @date 2015年12月12日 上午11:05:47
 */
public final class MarshallingCodecFactory {
	
	/**
	 * 创建Jboss Marshalling解码器MarshallingDecoder
	 */
	public static MarshallingDecoder buildMarshallingDecoder() {
		// 参数"serial"表示创建的是Java序列化工厂对象，它由jboss-marshalling-serial提供
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		
		UnmarshallerProvider provider = new DefaultUnmarshallerProvider(marshallerFactory, configuration);
		
		// 单个消息的序列化后的最大长度
		MarshallingDecoder decoder = new MarshallingDecoder(provider, 1024);
		
		return decoder;
	}
	
	/**
	 * 创建Jboss Marshalling编码器MarshallingEncoder
	 */
	public static MarshallingEncoder buildMarshallingEncoder() {
		final MarshallerFactory marshallerFactory = Marshalling.getProvidedMarshallerFactory("serial");
		final MarshallingConfiguration configuration = new MarshallingConfiguration();
		configuration.setVersion(5);
		
		MarshallerProvider provider = new DefaultMarshallerProvider(marshallerFactory, configuration);
		// 用于将序列化接口的POJO对象序列化为二进制数组
		MarshallingEncoder encoder = new MarshallingEncoder(provider);
		
		return encoder;
	}

}
