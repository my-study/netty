package com.hailiang.study.netty.demo03;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

/**
 * 支持TCP粘包的客户端
 * 
 * @classname com.hailiang.study.netty.demo03.TimeClient
 * @description TODO
 * @author hailiang.jiang
 * @date 2015年12月7日 下午8:54:28
 * @version: v1.0.0
 * @see
 */
public class TimeClient {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		new TimeClient().connect("127.0.0.1", port);
	}

	private void connect(String host, int port) throws Exception {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true).handler(new ChannelInitializer<SocketChannel>() {
				@Override
				protected void initChannel(SocketChannel sh) throws Exception {
					sh.pipeline().addLast(new LineBasedFrameDecoder(1024));
					sh.pipeline().addLast(new StringDecoder());
					sh.pipeline().addLast(new TimeClientHandler());
				}
			});
			
			// 发起异步连接操作
			ChannelFuture cf = bootstrap.connect(host, port).sync();
			
			// 等待客户端链路关闭
			cf.channel().closeFuture().sync();
		} finally {
			// 优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
		
	}
	
}
