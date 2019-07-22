import java.util.Properties;

import org.apache.kafka.clients.consumer.KafkaConsumer;

public class ConsummerProper {
	
	KafkaConsumer<String, String> consumer;
	
	public KafkaConsumer<String, String> getConsumer() {
		return consumer;
	}

	public void setConsumer(KafkaConsumer<String, String> consumer) {
		this.consumer = consumer;
	}

	public ConsummerProper() {
		Properties configs = new Properties();
		configs.put("bootstrap.servers", "localhost:9092");
		configs.put("session.timeout.ms", "10000");
		configs.put("group.id", "ankus-analzer-p");
		configs.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		configs.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		consumer = new KafkaConsumer<>(configs);
	}
}
