package cn.enjoyedu.commit;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;

/**
 * 异步手动提交
 */
public class CommitAsync {
    public static void main(String[] args) {
        //消息消费者
        Properties properties = KafaConstant.consumerConfig("CommitAsync", StringDeserializer.class, StringDeserializer.class);
        //TODO 取消自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        try{
            //订阅
            consumer.subscribe(Collections.singletonList(KafaConstant.CONSUMER_COMMIT_TOPIC));
            //循环拉取数据
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                if(records != null){
                    for(ConsumerRecord<String, String> record : records){
                        System.out.println(String.format("主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                                record.topic(), record.partition(), record.offset(), record.key(), record.value()));
                    }
                }
                //TODO 异步提交偏移量
                //consumer.commitAsync();
                //执行回调函数
                consumer.commitAsync(new OffsetCommitCallback() {
                    @Override
                    public void onComplete(Map<TopicPartition, OffsetAndMetadata> offsets, Exception e) {
                        if(e != null){
                            System.out.println("commit failed for offset");
                            System.out.println(offsets);
                            e.printStackTrace();
                        }
                    }
                });
            }
        }finally {
            consumer.close();
        }
    }
}
