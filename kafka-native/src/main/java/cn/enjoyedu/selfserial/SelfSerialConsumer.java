package cn.enjoyedu.selfserial;

import cn.enjoyedu.constant.KafaConstant;
import cn.enjoyedu.vo.UserVO;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;

public class SelfSerialConsumer {

    private static KafkaConsumer<String, UserVO> consumer = null;

    public static void main(String[] args) {
        consumer = new KafkaConsumer<String, UserVO>(KafaConstant.consumerConfig("selfserial",
                StringDeserializer.class,
                SelfDeserializer.class));
        try{
            consumer.subscribe(Collections.singletonList(KafaConstant.SELF_SERIAL_TOPIC));
            while (true){
                ConsumerRecords<String, UserVO> records = consumer.poll(Duration.ofMillis(500));
                for(ConsumerRecord<String, UserVO> record : records){
                    System.out.println(String.format(
                            "主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                            record.topic(),record.partition(),record.offset(),
                            record.key(),record.value()));
                }
            }
        }finally {
            consumer.close();
        }
    }
}
