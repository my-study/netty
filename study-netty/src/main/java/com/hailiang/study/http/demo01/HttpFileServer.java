package com.hailiang.study.http.demo01;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;
import io.netty.handler.stream.ChunkedWriteHandler;

public class HttpFileServer {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		new HttpFileServer().server(port, "/src/main/java/com/hailiang/study/");
	}

	private void server(int port, String url) throws Exception {
		EventLoopGroup parentGroup = new NioEventLoopGroup();
		EventLoopGroup childGroup = new NioEventLoopGroup();
		
		try {
			ServerBootstrap sb = new ServerBootstrap();
			sb.group(parentGroup, childGroup).channel(NioServerSocketChannel.class).childHandler(new HttpFileChildHandler(url));
			
			ChannelFuture cf = sb.bind("10.8.17.237", port).sync(); //10.8.17.237为本机ip地址
			System.out.println("Http文件目录服务器启动，网址是：http://10.8.17.237:" + port + url);
			
			cf.channel().closeFuture().sync();
		} finally {
			parentGroup.shutdownGracefully();
			childGroup.shutdownGracefully();
		}
	}
	
	private class HttpFileChildHandler extends ChannelInitializer<SocketChannel> {
		
		private final String url;
		
		public HttpFileChildHandler(String url) {
			this.url = url;
		}

		@Override
		protected void initChannel(SocketChannel sc) throws Exception {
			//Http请求消息解码器
			sc.pipeline().addLast("http-decoder", new HttpRequestDecoder());
			//HttpObjectAggregator解码器, 它的作用是将多个消息转换为单一的FullHttpRequest或者FullHttpResponse，原因是HTTP解码器在每个HTTP消息中会生成多个消息对象
			sc.pipeline().addLast("http-aggregator", new HttpObjectAggregator(65536));
			//Http响应编码器，对HTTP响应消息进行编码
			sc.pipeline().addLast("htt-encoder", new HttpResponseEncoder());
			//Chunked handler的主要作用是支持异步发送大的码流（例如大的文件传输），但不占用过多的内存，防止发生Java内存溢出错误
			sc.pipeline().addLast("http-chunked", new ChunkedWriteHandler());
			sc.pipeline().addLast("fileServerHandler", new HttpFileServerHandler(this.url));
		}
		
	}
	
}
