import org.apache.ignite.Ignite;
import org.apache.ignite.IgniteCache;
import org.apache.ignite.Ignition;
import org.apache.ignite.client.ClientCache;
import org.apache.ignite.client.ClientException;
import org.apache.ignite.client.IgniteClient;
import org.apache.ignite.configuration.ClientConfiguration;
import org.apache.ignite.configuration.IgniteConfiguration;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;

import java.util.Arrays;

public class Ignite_kafka_consummer {
    public static void main(String[] args ) {
        ClientConfiguration cfg = new ClientConfiguration().setAddresses("localhost:10800");

        try (IgniteClient igniteClient = Ignition.startClient(cfg)) {
            System.out.println(">>> Thin client put-get example started.");

            //*************KAFKA CONSUMMER************
            ConsummerProper consummerProper = new ConsummerProper();
            KafkaConsumer<String, String> consumer = consummerProper.getConsumer();
            consumer.subscribe(Arrays.asList("test")); //Get Process result using 'MLREQUEST_'+ appkey
            boolean bResultReceive = false;
            try {
                ClientCache<Integer, CacheMessage> cache = igniteClient.getOrCreateCache("baeldingCache");

                while(bResultReceive == false) {
                    ConsumerRecords<String, String> records = consumer.poll(1);
                    for (ConsumerRecord<String, String> record : records) {

                        if (record.topic().toString().equals("test")) {
                            String key = record.key();
                            String value = record.value();
                            cache.put(1, new CacheMessage(key, value));

                            System.out.println("Result:" + value);
                            if(key.equals("quit")) {
                                bResultReceive = true;
                            }
                            try {
                                consumer.commitSync();
                            } catch (Exception e) {
                                System.out.println(e.toString());
                            }
                        }
                    }
                }
            } finally {
                consumer.close();
            }

        }
        catch (ClientException e) {
            System.err.println("ClientExcept:" + e.getMessage());
        }
        catch (Exception e) {
            System.err.format("Unexpected failure: %s\n", e);
        }


    }
}
