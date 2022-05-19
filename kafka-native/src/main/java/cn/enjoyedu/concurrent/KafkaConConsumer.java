package cn.enjoyedu.concurrent;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Collections;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 多线程下的消费者
 */
public class KafkaConConsumer {

    private static ExecutorService executorService = Executors.newFixedThreadPool(KafaConstant.CONCURRENT_PARTITIONS_COUNT);

    private static class ConsumerWorker implements Runnable{
        private KafkaConsumer<String, String> consumer;
        public ConsumerWorker(Map<String, Object> config, String topic){
            Properties properties = new Properties();
            properties.putAll(config);
            this.consumer = new KafkaConsumer<String, String>(properties);
            consumer.subscribe(Collections.singletonList(topic));
        }
        @Override
        public void run() {
            final String id = Thread.currentThread().getId() + "-" + System.identityHashCode(consumer);
            try{
                while (true){
                    ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(500));
                    for(ConsumerRecord<String, String> record : records){
                        System.out.println(id+"|"+String.format(
                                "主题：%s，分区：%d，偏移量：%d，" +
                                        "key：%s，value：%s",
                                record.topic(),record.partition(),
                                record.offset(),record.key(),record.value()));
                    }
                }
            }finally {
                consumer.close();
            }
        }
    }

    public static void main(String[] args) {
        //消费者配置实例
        Map<String, Object> config = KafaConstant.consumerConfigMap("concurrent",
                StringDeserializer.class, StringDeserializer.class);
        for(int i = 0;i < KafaConstant.CONCURRENT_PARTITIONS_COUNT;i++){
            executorService.submit(new ConsumerWorker(config, KafaConstant.CONCURRENT_USER_INFO_TOPIC));
        }
    }
}
