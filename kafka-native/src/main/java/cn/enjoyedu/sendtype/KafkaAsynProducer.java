package cn.enjoyedu.sendtype;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

/**
 * 异步发送方式
 */
public class KafkaAsynProducer {

    private static KafkaProducer<String, String> producer = null;

    public static void main(String[] args) {
        /*消息生产者*/
        producer = new KafkaProducer<String, String>(KafaConstant.producerConfig(StringSerializer.class, StringSerializer.class));
        try {
            /*待发送的消息实例*/
            ProducerRecord<String, String> record;
            try {
                record = new ProducerRecord<>(KafaConstant.HELLO_TOPIC, "teacher14", "stephen");
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(recordMetadata != null){
                            System.out.println("offset: " + recordMetadata.offset() + " - " + "partition: " + recordMetadata.partition());
                        }
                    }
                });
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }finally {
            producer.close();
        }
    }
}
