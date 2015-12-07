package com.hailiang.study.aio.demo01;

import java.util.concurrent.TimeUnit;

public class TimeClient {

	public static void main(String[] args) throws Exception {
		int port = 8080;
		for (int i = 0; i < 10; i++) {
			new Thread(new AsyncTimeClientHandler("127.0.0.1", port), "AIO-AsyncTimeClientHandler-001").start();
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
}
