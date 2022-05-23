package cn.enjoyedu.service;

import org.apache.kafka.clients.producer.RecordMetadata;
import org.springframework.kafka.support.ProducerListener;

/**
 * 类说明：发送者确认
 */
public class SendListener implements ProducerListener{
    @Override
    public void onSuccess(String topic, Integer partition, Object key, Object value, RecordMetadata recordMetadata) {
        System.out.println("offset:"+recordMetadata.offset()+"-"
                +"partition:"+recordMetadata.partition());
    }

    @Override
    public void onError(String topic, Integer partition, Object key, Object value, Exception exception) {

    }

    @Override
    public boolean isInterestedInSuccess() {
        return true;
    }
}
