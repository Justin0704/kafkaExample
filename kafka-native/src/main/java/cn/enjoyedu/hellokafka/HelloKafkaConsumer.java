package cn.enjoyedu.hellokafka;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

public class HelloKafkaConsumer {
    public static void main(String[] args) {
        //消费者三个属性必须指定（broker地址清单、key和value的反序列化器）
        Properties properties = new Properties();
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        properties.put("key.deserializer", StringDeserializer.class);
        properties.put("value.deserializer", StringDeserializer.class);

        //群组并非完全必须
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, "test1");
        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        try{
            //TODO 消费者订阅主题，可以订阅多个
            consumer.subscribe(Collections.singletonList(KafaConstant.HELLO_TOPIC));
            while (true){
                //拉取
                ConsumerRecords<String, String> records =   consumer.poll(Duration.ofMillis(500));
                for(ConsumerRecord<String, String> consumerRecord : records){
                    System.out.println(String.format("Topic: %s , 偏移量：%d，" + "key：%s, value：%s", consumerRecord.topic(),
                            consumerRecord.offset(), consumerRecord.key(), consumerRecord.value()));
                }
            }
        }finally {
            consumer.close();
        }
    }
}
