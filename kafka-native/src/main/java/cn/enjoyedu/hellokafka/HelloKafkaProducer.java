package cn.enjoyedu.hellokafka;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class HelloKafkaProducer {
    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
        try{
            ProducerRecord<String, String> record;
            //发送消息
            for(int i = 0;i < 4;i++){
                record = new ProducerRecord<>(KafaConstant.HELLO_TOPIC, String.valueOf(i), "justin");
                producer.send(record);
                System.out.println(i + " ,message is sent");
            }
        }finally {
            producer.close();
        }

    }
}
