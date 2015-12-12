package com.hailiang.study.codec.demo02.client;

import com.hailiang.study.codec.demo02.marshalling.MarshallingCodecFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

public class UserClient {

	public static void main(String[] args) throws Exception {
		new UserClient().connect("127.0.0.1", 8080);
	}

	private void connect(String host, int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap b = new Bootstrap();
			b.group(group).channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new UserClientChildChannel());
			
			ChannelFuture cf = b.connect(host, port).sync();
			cf.channel().closeFuture().sync();
		} finally {
			group.shutdownGracefully();
		}
	}
	
	public class UserClientChildChannel extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel sc) throws Exception {
			sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingDecoder());
			sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingEncoder());
			sc.pipeline().addLast(new UserClientHandler());
		}
	}
}
