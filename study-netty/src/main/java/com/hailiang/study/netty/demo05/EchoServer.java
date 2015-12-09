package com.hailiang.study.netty.demo05;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.FixedLengthFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class EchoServer {

	public static void main(String[] args) {
		new EchoServer().bind(8080);
	}

	private void bind(int port) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.handler(new LoggingHandler(LogLevel.DEBUG))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						sc.pipeline().addLast(new FixedLengthFrameDecoder(20));
						sc.pipeline().addLast(new StringDecoder());
						sc.pipeline().addLast(new EchoServerHandler());
					}
				});
			
			// 绑定端口，同步等待成功
			ChannelFuture cf = sb.bind(port).sync();
			
			// 等待服务端，监听端口关闭
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 优雅退出， 释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
	}
	
}
