package com.hailiang.study.codec.demo02.server;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

import com.hailiang.study.codec.demo02.marshalling.MarshallingCodecFactory;

public class UserServer {

	public static void main(String[] args) throws Exception {
		new UserServer().bind(8080);
	}

	private void bind(int port) throws Exception {
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(parentGroup, childGroup).channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new UserChildChannel());
			
			ChannelFuture cf = sb.bind(port).sync();
			cf.channel().closeFuture().sync();
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
		
	}

	private class UserChildChannel extends ChannelInitializer<SocketChannel> {
		@Override
		protected void initChannel(SocketChannel sc) throws Exception {
			sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingDecoder());
			sc.pipeline().addLast(MarshallingCodecFactory.buildMarshallingEncoder());
			sc.pipeline().addLast(new UserServerHandler());
		}
	}
}
