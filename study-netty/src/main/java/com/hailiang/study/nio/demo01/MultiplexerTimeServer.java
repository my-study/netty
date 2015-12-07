package com.hailiang.study.nio.demo01;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 负责轮询多路复用器Selector, 可以处理多个客户端的并发接入
 * 
 * @classname com.hailiang.study.nio.demo01.MultiplexerTimeServer
 * @description TODO
 * @author hailiang.jiang
 * @date 2015年12月3日 下午6:14:14
 * @version: v1.0.0
 * @see
 */
public class MultiplexerTimeServer implements Runnable {
	
	private Selector selector;
	private ServerSocketChannel serverChannel;
	private volatile boolean stop = false;
	
	/**
	 * 初始化多路复用器、绑定监听端口
	 * @param port
	 */
	public MultiplexerTimeServer(int port) {
		try {
			serverChannel = ServerSocketChannel.open();
			serverChannel.configureBlocking(false);
			serverChannel.socket().bind(new InetSocketAddress(port), 1024);
			
			selector = Selector.open();
			serverChannel.register(selector, SelectionKey.OP_ACCEPT);
			
			System.out.println("The time server is start in port : " + port);
		} catch (IOException ie) {
			ie.printStackTrace();
			System.exit(-1);
		}
	}
	
	public void stop() {
		this.stop = true;
	}

	@Override
	public void run() {
		while (!stop) {
			try {
				selector.select(1000);
				Set<SelectionKey> selectionKeys = selector.selectedKeys();
				Iterator<SelectionKey> it = selectionKeys.iterator();
				
				SelectionKey key = null;
				while (it.hasNext()) {
					key = it.next();
					it.remove();
					try {
						handleInput(key);
					} catch (Exception e) {
						if (key != null) {
							key.cancel();
							if (key.channel() != null) {
								key.channel().close();
							}
						}
					}
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		// 多路复用器关闭后，所有注册在上面的Channel和Pipe等资源都会被自动去注册并关闭，所有不需要重复释放资源
		if (selector != null) {
			try {
				selector.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	private void handleInput(SelectionKey key) throws IOException {
		if (key.isValid()) {
			// 处理新接入的请求消息
			if (key.isAcceptable()) {
				// 接收新连接
				ServerSocketChannel ssc = (ServerSocketChannel) key.channel();
				SocketChannel sc = ssc.accept();
				sc.configureBlocking(false);
				// 将新连接注册到selector上
				sc.register(selector, SelectionKey.OP_READ);
			}
			
			//读取数据
			if (key.isReadable()) {
				SocketChannel sc = (SocketChannel) key.channel();
				
				ByteBuffer readBuffer = ByteBuffer.allocate(1024);
				int readBytes = sc.read(readBuffer);
				if (readBytes > 0) {
					readBuffer.flip();
					byte[] bytes = new byte[readBuffer.remaining()];
					readBuffer.get(bytes);
					
					String body = new String(bytes, "UTF-8");
					System.out.println("The time Server receive order: " + body);
					String currentTime = "QUERY TIME ORDER".equalsIgnoreCase(body) ? new java.util.Date(System.currentTimeMillis()).toString() : "BAD ORDER";
					
					doWrite(sc, currentTime);
				} else if (readBytes < 0) {
					// 对端链路关闭
					key.cancel();
					sc.close();
				} else {
					//读到0个字节，忽略
				}
			}
		}
	}
	
	private void doWrite(SocketChannel channel, String response) throws IOException {
		if (response != null && response.trim().length() > 0) {
			byte[] bytes = response.getBytes();
			ByteBuffer writeBuffer = ByteBuffer.allocate(bytes.length);
			writeBuffer.put(bytes);
			writeBuffer.flip();
			channel.write(writeBuffer);
		}
	}

}
