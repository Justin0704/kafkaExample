package cn.enjoyedu.commit;

import cn.enjoyedu.constant.KafaConstant;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

/**
 * 生产者
 */
public class Producer {

    private static KafkaProducer<String, String> producer = null;

    public static void main(String[] args) {
        //发送配置的实例
        Properties properties = new Properties();
        //添加broker地址清单
        properties.put("bootstrap.servers", "127.0.0.1:9092");
        /*key的序列化器*/
        properties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        /*value的序列化器*/
        properties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        //消息生产者
        producer = new KafkaProducer<String, String>(properties);
        try {
            /*待发送的消息实例*/
            ProducerRecord<String, String> record;
            try {
                for(int i = 0;i <50;i++){
                    record = new ProducerRecord<>(KafaConstant.CONSUMER_COMMIT_TOPIC, "key" + i, "value" + i);
                    producer.send(record);
                    System.out.println("数据【" + record + "】已发送");
                }
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }finally {
            producer.close();
        }
    }
}
