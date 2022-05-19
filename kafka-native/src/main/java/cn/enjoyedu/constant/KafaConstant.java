package cn.enjoyedu.constant;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Deserializer;
import org.apache.kafka.common.serialization.Serializer;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

public class KafaConstant {

    public static final String HELLO_TOPIC = "hello-topic";

    //胜差这和消费者共用配置常量
    public static final String LOCAL_BROKER = "127.0.0.1:9092";
    public static final String BROKER_LIST = "127.0.0.1:9093";

    /**
     * 生产者配置
     * @param keySerializerClazz
     * @param valueSerializerClazz
     * @return
     */
    public static Properties producerConfig(Class<? extends Serializer> keySerializerClazz,
                                            Class<? extends Serializer> valueSerializerClazz){
        Properties properties = new Properties();
        properties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCAL_BROKER);
        properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, keySerializerClazz);
        properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, valueSerializerClazz);
        return properties;
    }

    /**
     * 消费者配置
     * @param groupId
     * @param keyDeserializerClazz
     * @param valueDeserializerClazz
     * @return
     */
    public static Properties consumerConfig(String groupId,
                                            Class<? extends Deserializer> keyDeserializerClazz,
                                            Class<? extends Deserializer> valueDeserializerClazz){
        Properties properties = new Properties();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCAL_BROKER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClazz);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClazz);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return properties;
    }

    public static Map<String,Object> consumerConfigMap(String groupId,
                                                       Class<? extends Deserializer> keyDeserializerClazz,
                                                       Class<? extends Deserializer> valueDeserializerClazz){
        Map<String,Object> properties = new HashMap<String, Object>();
        properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, LOCAL_BROKER);
        properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, keyDeserializerClazz);
        properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, valueDeserializerClazz);
        properties.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        return properties;
    }
}
