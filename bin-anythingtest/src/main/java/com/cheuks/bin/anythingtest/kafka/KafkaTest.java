package com.cheuks.bin.anythingtest.kafka;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import kafka.consumer.Consumer;
import kafka.consumer.ConsumerConfig;
import kafka.consumer.ConsumerIterator;
import kafka.consumer.KafkaStream;
import kafka.javaapi.consumer.ConsumerConnector;

public class KafkaTest {

	private Properties properties = new Properties();
	Map<String, Integer> topicCountMap = new HashMap<String, Integer>();
	{
		properties.put("zookeeper.connect", "192.168.1.30:2181");// 声明zk
		// properties.put("metadata.broker.list", "192.168.1.30:9092");// 声明zk
		properties.put("bootstrap.servers", "192.168.1.30:9092");
		properties.put("group.id", "DemoConsumer");
		properties.put("client.id", "DemoProducer");
		properties.put("key.serializer", "org.apache.kafka.common.serialization.IntegerSerializer");
		properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

		topicCountMap.put("A", 1);
	}

	public void Producer() {
		KafkaProducer<Integer, String> producer = new KafkaProducer<Integer, String>(properties);
		producer.send(new ProducerRecord<Integer, String>("A", "abc"));
	}

	public void Consumer() {
		ConsumerConnector connector = Consumer.createJavaConsumerConnector(new ConsumerConfig(properties));

		Map<String, List<KafkaStream<byte[], byte[]>>> streams = connector.createMessageStreams(topicCountMap);
		List<KafkaStream<byte[], byte[]>> messages = streams.get(topicCountMap);
		ConsumerIterator<byte[], byte[]> it;
		for (KafkaStream<byte[], byte[]> en : messages) {
			it = en.iterator();
			while (it.hasNext()) {
				System.out.println(new String(it.next().message()));
			}
		}
	}

	public static void main(String[] args) {
		KafkaTest kt = new KafkaTest();
		kt.Producer();
		// kt.Consumer();
	}
}
