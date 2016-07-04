package com.cheuks.bin.anythingtest.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

/**
 * 
 * <p>
 * Title:Consumertest
 * </p>
 * <p>
 * Description: 消费者端
 * </p>
 * 
 * @author xiefg
 * @date 2016年5月17日下午5:06:36
 */
public class k2 extends Thread {
	
	volatile 

	private final ConsumerConnector consumer;// 连接
	private final String topic;

	public static void main(String[] args) {
		k2 consumerThread = new k2("B");
		consumerThread.start();
	}

	public k2(String topic) {
		consumer = Consumer.createJavaConsumerConnector(createConsumerConfig());
		this.topic = topic;
	}

	private static ConsumerConfig createConsumerConfig() {
		Properties props = new Properties();
		// 设置zookeeper的链接地址
		props.put("zookeeper.connect", "192.168.1.30:2181");
		// 设置group id
		props.put("group.id", "10");
		// kafka的group 消费记录是保存在zookeeper上的, 但这个信息在zookeeper上不是实时更新的, 需要有个间隔时间更新
		props.put("auto.commit.interval.ms", "1000");
		props.put("zookeeper.session.timeout.ms", "10000");
		return new ConsumerConfig(props);
	}

	public void run() {
		// 构建具体的流
		Map<String, Integer> topickMap = new HashMap<String, Integer>();
		topickMap.put(topic, 1);
		Map<String, List<KafkaStream<byte[], byte[]>>> streamMap = consumer.createMessageStreams(topickMap);
		KafkaStream<byte[], byte[]> stream = streamMap.get(topic).get(0);
		ConsumerIterator<byte[], byte[]> it = stream.iterator();
		System.out.println("============【结果】========");
		while (it.hasNext()) {
			System.err.println("get data:" + new String(it.next().message()));
			try {
				Thread.sleep(1000);// 间隔1
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
