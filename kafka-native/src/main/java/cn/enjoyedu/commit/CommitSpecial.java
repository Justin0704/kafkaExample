package cn.enjoyedu.commit;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 特定提交偏移量
 */
public class CommitSpecial {
    public static void main(String[] args) {

        //消费者消费
        Properties properties = KafaConstant.consumerConfig("CommitSpecial",
                StringDeserializer.class, StringDeserializer.class);
        //TODO 取消自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        //定义一个map
        Map<TopicPartition, OffsetAndMetadata> currOffsets = new HashMap<>();
        int count = 0;
        try{
            //订阅
            consumer.subscribe(Collections.singletonList(KafaConstant.CONSUMER_COMMIT_TOPIC));
            //拉取
            while (true){
                ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                for(ConsumerRecord<String, String> record : records){
                    System.out.println(String.format("主题：%s，分区：%d，偏移量：%d，key：%s，value：%s",
                            record.topic(), record.partition(), record.offset(), record.key(), record.value()));

                    currOffsets.put(new TopicPartition(record.topic(), record.partition()),
                            new OffsetAndMetadata(record.offset() + 1, "meta data"));
                    if(count % 11 == 0){
                        //TODO 特定提交，（异步方式，加入了偏移量）， 没11条提交一次
                        consumer.commitAsync(currOffsets, null);
                    }
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            //TODO 关闭之前，同步提交一次偏移量
            consumer.commitSync();
            consumer.close();
        }
    }
}
