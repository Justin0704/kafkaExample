package cn.enjoyedu.selfserial;

import cn.enjoyedu.constant.KafaConstant;
import cn.enjoyedu.vo.UserVO;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

public class SelfSerialProducer {
    private static KafkaProducer<String, UserVO> producer = null;

    public static void main(String[] args) {
        /*消息生产者*/
        producer = new KafkaProducer<String, UserVO>(KafaConstant.producerConfig(StringSerializer.class,SelfSerializer.class));
        try{
            ProducerRecord<String, UserVO> record;
            try{
                record = new ProducerRecord<String, UserVO>(KafaConstant.SELF_SERIAL_TOPIC, "user01", new UserVO(1, "justin"));
                producer.send(record);
                System.out.println("sent message ...");
            }catch (Exception e){
                e.printStackTrace();
            }
        }finally {
            producer.close();
        }
    }
}
