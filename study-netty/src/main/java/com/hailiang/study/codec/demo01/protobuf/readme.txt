Protobuf开发环境搭建
	下载Protobuf的最新Windows版本，可以从我的百度云盘下载(protoc-2.5.0-win32.zip)。
	
	protoc.exe工具主要根据.proto文件生成代码, 参照com.hailiang.study.codec.demo01.protobuf.proto下.
	
	切换到代码所com.hailiang.study.codec.demo01.protobuf.proto在目录，通过protoc.ext命名行生成java代码，命令如下：
		E:/workspace/opensource/protobuf-2.5.0/protoc-2.5.0-win32/protoc.exe --java_out=./ *.proto