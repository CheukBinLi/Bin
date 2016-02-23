#生成JAVA文件命令
#thrift -r -gen java demo2.thrift
namespace java com.cheuks.bin.anythingtest.thrift
struct model{
	1:i32 id,
	2:string name,
	3:string remark
}

exception notFindModel{
	1:string message
}

service modelServer{
	model getModel(1:i32 id)throws (1:notFindModel nfm),
	list<model>getList()
}