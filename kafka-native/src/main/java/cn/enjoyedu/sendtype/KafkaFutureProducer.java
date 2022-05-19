package cn.enjoyedu.sendtype;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.concurrent.Future;

/**
 * 同步发送消息方式，未来某个时候get发送结果
 */
public class KafkaFutureProducer {

    private static KafkaProducer<String, String> producer = null;

    public static void main(String[] args) {
        //TODO 消息生产者
        producer = new KafkaProducer<String, String>(KafaConstant.producerConfig(StringSerializer.class, StringSerializer.class));
        try {
            ProducerRecord<String, String> record;
            try{
                record = new ProducerRecord<String, String>(KafaConstant.HELLO_TOPIC, "teacher10", "willon");
                Future<RecordMetadata> future = producer.send(record);
                RecordMetadata metadata = future.get();
                if(metadata != null){
                    System.out.println("offset: " + metadata.offset() + " - " + "partition: " + metadata.partition());
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }finally {
            producer.close();
        }
    }
}
