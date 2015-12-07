package com.hailiang.study.netty.demo02;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import com.hailiang.study.netty.demo02.TimeClientHandler;

public class TimeClient {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		new TimeClient().connect("127.0.0.1", port);
	}

	private void connect(String host, int port) throws Exception {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap b = new Bootstrap();
			b.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(new TimeClientHandler());
					}
				});
			
			// 发起异步连接操作
			ChannelFuture cf = b.connect(host, port).sync();
			
			// 等待客户端链路关闭
			cf.channel().closeFuture().sync();
		} finally {
			// 优雅退出， 释放NIO线程组 
			group.shutdownGracefully();
		}
	}
	
}
