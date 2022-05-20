package cn.enjoyedu.selfpartition;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.concurrent.Future;

public class SysPartitionProducer {
    private static KafkaProducer<String,String> producer = null;

    public static void main(String[] args) {
        /*消息生产者*/
        Properties properties
                = KafaConstant.producerConfig(StringSerializer.class,
                StringSerializer.class);
        producer = new KafkaProducer<String, String>(properties);
        try {
            /*待发送的消息实例*/
            ProducerRecord<String,String> record;
            try {
                record = new ProducerRecord<String,String>(
                        KafaConstant.SELF_PARTITION_TOPIC,"teacher01",
                        "justin");
                Future<RecordMetadata> future = producer.send(record);
                System.out.println("Do other something");
                RecordMetadata recordMetadata = future.get();
                if(null!=recordMetadata){
                    System.out.println(String.format("偏移量：%s,分区：%s",
                            recordMetadata.offset(),
                            recordMetadata.partition()));
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        } finally {
            producer.close();
        }
    }
}
