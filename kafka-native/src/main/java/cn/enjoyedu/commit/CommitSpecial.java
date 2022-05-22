package cn.enjoyedu.commit;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.consumer.OffsetAndMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * 特定提交偏移量
 */
public class CommitSpecial {
    public static void main(String[] args) {

        //消费者消费
        Properties properties = KafaConstant.consumerConfig("CommitSpecial", StringDeserializer.class, StringDeserializer.class);
        //TODO 修改自动提交
        properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, false);

        KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
        //定义一个map
        Map<TopicPartition, OffsetAndMetadata> currOffsets = new HashMap<>();
        int count = 0;
        try{

        }catch (Exception e){
            e.printStackTrace();
        }finally {
            consumer.close();
        }
    }
}
