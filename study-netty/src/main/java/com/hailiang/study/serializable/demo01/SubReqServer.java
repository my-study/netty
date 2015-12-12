package com.hailiang.study.serializable.demo01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;

public class SubReqServer {

	public static void main(String[] args) {
		int port = 8080;
		new SubReqServer().bind(port);
	}

	
	private void bind(int inetPort) {
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(bossGroup, workerGroup)
				.channel(NioServerSocketChannel.class)
				.option(ChannelOption.SO_BACKLOG, 100)
				.handler(new LoggingHandler(LogLevel.INFO))
				.childHandler(new ChannelInitializer<SocketChannel>() {
					@Override
					protected void initChannel(SocketChannel sc) throws Exception {
						/**
						 * 首先创建了一个新的ObjectDecoder，它负责对实现Serializable的POJO对象进行解码
						 * 它有多个构造函数，支持不同的ClassResolver，在此我们使用weakCachingConcurrentResolver创建线程安全
						 * 的WeakReferenceMap对类加载器进行缓存，它支持多线程并发访问，当虚拟机内存不足时，会释放缓存中的内存，
						 * 防止内存泄漏。为了防止异常码流和解码错位导致的内存溢出，这里将单个对象最大序列化后的字节数组长度设置为1	M，
						 * 作为例程它已经足够使用了。
						 **/
						sc.pipeline().addLast(new ObjectDecoder(1024 * 1024, ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
						//添加对象编码器 在服务器对外发送消息的时候自动将实现序列化的POJO对象编码
						sc.pipeline().addLast(new ObjectEncoder());
						sc.pipeline().addLast(new SubReqServerHandler());
					}
				});
			
			ChannelFuture cf = sb.bind(inetPort).sync();
			
			// 等待服务器监听端口关闭
			cf.channel().closeFuture().sync();
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			// 优雅退出，释放线程池资源
			bossGroup.shutdownGracefully();
			workerGroup.shutdownGracefully();
		}
		
	}
	
}
