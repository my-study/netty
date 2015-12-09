package com.hailiang.study.netty.demo04;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;

public class EchoClient {

	public static void main(String[] args) {
		new EchoClient().connect(NettyConstant.HOST, NettyConstant.PORT);
	}

	private void connect(String host, int port) {
		// 配置客户端NIO线程组
		EventLoopGroup group = new NioEventLoopGroup();
		
		try {
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group).channel(NioSocketChannel.class).option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						ByteBuf delimiter = Unpooled.copiedBuffer(NettyConstant.DELIMITER.getBytes());
						sc.pipeline().addLast(new DelimiterBasedFrameDecoder(1024, delimiter));
						sc.pipeline().addLast(new StringDecoder());
						sc.pipeline().addLast(new EchoClientHandler());
					}
				});
			
			// 发起异步连接操作
			ChannelFuture cf = bootstrap.connect(host, port).sync();
			
			// 等待客户端链路关闭
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 优雅的退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
}
