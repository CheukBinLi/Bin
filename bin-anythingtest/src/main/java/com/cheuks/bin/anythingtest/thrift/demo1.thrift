#生成JAVA文件命令
#thrift -r -gen java demo1.thrift
namespace java com.cheuks.bin.anythingtest.thrift
service HelloWordService{
	string sayHello(1:string name);
}