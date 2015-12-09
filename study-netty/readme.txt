BIO: 阻塞I/O

NIO: 非阻塞I/O
	实例package路径：com.hailiang.study.nio

AIO: 事件驱动I/O
	com.hailiang.study.netty.demo01: 第一个netty应用，目的是为了学习netty的使用
	com.hailiang.study.netty.demo02: 未考虑TCP粘包，导致功能性异常案例
	com.hailiang.study.netty.demo03: TCP粘包问题:利用LineBasedFrameDecoder+StringDecoder解决TCP粘包问题
		LineBasedFrameDecoder的工作原理，是依次遍历ByteBuf中的可读字节，判断看是否有“\n”或者“\r\n”， 如果有，就以此位置为结束位置，从可读索引到结束位置区间的字节就组成了一行。
	com.hailiang.study.netty.demo04: TCP粘包问题:分隔符解码器(DelimiterBasedFrameDecoder)的应用
		