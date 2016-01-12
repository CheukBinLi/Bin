package com.cheuks.bin.net.server;

import com.cheuks.bin.net.server.event.EventInfo;
import com.cheuks.bin.net.server.handler.ServiceHandler;
import com.cheuks.bin.net.util.ConstantType;
import com.cheuks.bin.net.util.Serializ;

public interface Server extends ConstantType {

	//	/***
	//	 * 服务默认类型
	//	 */
	//	public static final int SERVICE_TYPE_RMI = 1, SERVICE_TYPE_MESSAGE = 2;

	/***
	 * 启动服务
	 * 
	 * @return
	 * @throws Throwable
	 */
	Server start() throws Throwable;

	/***
	 * 
	 * @param maxConnection
	 *            并发连接数
	 * @param timeOut
	 *            心跳连接超时
	 * @return
	 * @throws Throwable
	 */
	Server start(Integer maxConnection, long timeOut) throws Throwable;

	//	Server stop() throws Throwable;

	/***
	 * 
	 * @param port
	 *            端口
	 * @return
	 * @throws Throwable
	 */
	Server addService(Integer port) throws Throwable;

	/***
	 * 服务事件接口
	 * 
	 * @param eventInfos
	 *            对应的服务实现（读，处理，写）
	 * @param serviceType
	 *            对应服务类型
	 * @return
	 * @throws Throwable
	 */
	Server addEventHandle(EventInfo eventInfos, Integer serviceType) throws Throwable;

	/***
	 * 
	 * @param handler
	 *            服务处理实现:RMI/数据搜索。。。。
	 * @return
	 * @throws Throwable
	 */
	Server addHandler(ServiceHandler... handler) throws Throwable;

	/***
	 * 序列化实现
	 * 
	 * @param serializ
	 * @return
	 */
	Server setSerializ(Serializ serializ);

}
