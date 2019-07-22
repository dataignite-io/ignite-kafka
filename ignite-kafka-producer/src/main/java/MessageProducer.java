import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;

import java.util.Properties;

public class MessageProducer {
    public KafkaProducer<String, String> producer;

    private Properties createProducerConfig(String brokers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", brokers);
        props.put("acks", "all");
        props.put("retries", 10);
        props.put("batch.size", 16384);
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        return props;
    }
    public void initProducere() {
        Properties props = createProducerConfig("localhost:9092");
        producer = new KafkaProducer<String, String>(props);
        sendMessage("test", "hi");
    }
    private void sendMessage(String key, final String value) {

        try {
            //json has appkey
            producer.send(new ProducerRecord<String, String>(key, value), new Callback() {
                public void onCompletion(RecordMetadata metadata, Exception e) {
                    if (e != null) {
                        e.printStackTrace();
                    }
                    System.out.println("Sent:" + value + ", Partition: " + metadata.partition() + ", Offset: "
                            + metadata.offset());
                }
            });
        }
        catch (Exception e) {
            System.out.println(e.toString());
        }
        producer.close();

    }

    public static void main(String[] args) {
        MessageProducer producer = new MessageProducer();
        producer.initProducere();

    }
}
