package cn.enjoyedu.commit;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

/**
 * 异步和同步结合（提交偏移量）
 */
public class CommitSyncAndAsync {
    public static void main(String[] args) {
        //消费者消费
        Properties properties = KafaConstant.consumerConfig("CommitSyncAndAsync", StringDeserializer.class, StringDeserializer.class);
        //TODO 关闭自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        try{
            //订阅
            consumer.subscribe(Collections.singletonList(KafaConstant.CONSUMER_COMMIT_TOPIC));
            //拉取
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                if(records != null){
                    for(ConsumerRecord<String, String> record : records){
                        System.out.println(String.format("主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                                record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                    }
                }
                //TODO 异步提交
                consumer.commitAsync();
            }
        }catch (Exception e){
            System.out.println("async commit failed");
            e.printStackTrace();
        }finally {
            try {
                //TODO 防止数据异常， 同步提交一下
                consumer.commitSync();
            }finally {
                consumer.close();
            }
        }
    }
}
