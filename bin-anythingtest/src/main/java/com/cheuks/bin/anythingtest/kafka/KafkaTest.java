package com.cheuks.bin.anythingtest.kafka;

import java.util.Arrays;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import kafka.Kafka;

public class KafkaTest {

	private Properties props = new Properties();

	/***
	 * 生产者
	 */
	void P() {
		props.put("bootstrap.servers", "192.168.1.30:9092");
		props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
		props.put("num.partitions", 5);
		// props.put("acks", "all");
		props.put("retries", 1);
	}

	/***
	 * 消费都
	 */
	void C() {
		props.put("bootstrap.servers", "192.168.1.30:9092");
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.setProperty("group.id", "66");
		props.setProperty("auto.commit.enable", "true");
		props.setProperty("offsets.commit.required.acks", "-1");
		props.setProperty("auto.offset.reset", "earliest");
	}

	public void producer(String msg) {
		P();
		Producer<String, String> producer = new KafkaProducer<String, String>(props);
		producer.send(new ProducerRecord<String, String>("AA1", "哇哈哈xx"));
		producer.flush();
		producer.close();
	}

	public void consumer() {
		C();
		Consumer<String, String> consumer = new KafkaConsumer<String, String>(props);

		consumer.subscribe(Arrays.asList("AA1"));
		for (int i = 0; i < 2; i++) {
			ConsumerRecords<String, String> records = consumer.poll(10);
			System.out.println(records.count());
			for (ConsumerRecord<String, String> record : records) {
				// consumer.seekToBeginning(new TopicPartition(record.topic(), record.partition()));
				System.out.println(record);
				// consumer.seek(new TopicPartition(record.topic(), record.partition()), record.offset());
				System.err.println("topic:" + record.topic() + "   partition: " + record.partition());
			}
		}
		// consumer.commitAsync(new OffsetCommitCallback() {
		//
		// public void onComplete(Map<TopicPartition, org.apache.kafka.clients.consumer.OffsetAndMetadata> offsets, Exception exception) {
		// System.out.println("");
		// }
		// });
		consumer.commitSync();
	}

	public static void main(String[] args) {
		KafkaTest k = new KafkaTest();
		// k.producer("aaaa");
		k.consumer();
	}

}
