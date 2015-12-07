package com.hailiang.study.nio.demo01;

public class TimeServer {

	public static void main(String[] args) {
		int port = 8080;
		MultiplexerTimeServer timeServer = new MultiplexerTimeServer(port);
		
		new Thread(timeServer, "NIO-MultiplexerTimeServer-001").start();
	}
}
