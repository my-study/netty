package com.hailiang.study.nio.demo01;

import java.util.concurrent.TimeUnit;

public class TimeClient {

	public static void main(String[] args) throws Exception {
		for (int i = 0; i < 10; i++) {
			new Thread(new TimeClientHandle("127.0.0.1", 8080), "TimeClient-" + i).start();
			TimeUnit.SECONDS.sleep(1);
		}
	}
	
}
