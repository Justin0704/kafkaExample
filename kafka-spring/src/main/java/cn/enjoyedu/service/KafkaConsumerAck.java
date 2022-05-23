package cn.enjoyedu.service;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.listener.AcknowledgingMessageListener;
import org.springframework.kafka.support.Acknowledgment;

public class KafkaConsumerAck implements AcknowledgingMessageListener<String, String>{
    @Override
    public void onMessage(ConsumerRecord<String, String> consumerRecord, Acknowledgment acknowledgment) {
        String name = Thread.currentThread().getName();
        System.out.println(name+"|"+String.format(
                "主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                consumerRecord.topic(),consumerRecord.partition(),consumerRecord.offset(),
                consumerRecord.key(),consumerRecord.value()));
        //偏移量确认（手动的过程）
        acknowledgment.acknowledge();
    }
}
