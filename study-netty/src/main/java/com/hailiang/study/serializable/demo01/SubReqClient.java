package com.hailiang.study.serializable.demo01;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class SubReqClient {

	public static void main(String[] args) {
		new SubReqClient().connect("127.0.0.1", 8080);
	}

	private void connect(String host, int port) {
		EventLoopGroup group = new NioEventLoopGroup();
		try {
			//服务器辅助启动类配置
			Bootstrap bootstrap = new Bootstrap();
			bootstrap.group(group)
				.channel(NioSocketChannel.class)
				.option(ChannelOption.TCP_NODELAY, true)
				.handler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						//添加POJO对象解码器 禁止缓存类加载器
						sc.pipeline().addLast(new ObjectDecoder(1024, ClassResolvers.cacheDisabled(this.getClass().getClassLoader())));
						//设置发送消息编码器
						sc.pipeline().addLast(new ObjectEncoder());
						//设置网络IO处理器
						sc.pipeline().addLast(new SubReqClientHandler());
					}
				});
			
			ChannelFuture cf = bootstrap.connect(host, port).sync();
			
			cf.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			//优雅退出，释放NIO线程组
			group.shutdownGracefully();
		}
	}
	
}
