package cn.enjoyedu.rebalance;

import cn.enjoyedu.constant.KafaConstant;
import cn.enjoyedu.vo.UserVO;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分区再均衡，多线程下的生产者
 */
public class RebalanceProducer {

    private static final int MSG_SIZE = 50;
    //定义一个线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //发令枪
    private static CountDownLatch countDownLatch = new CountDownLatch(MSG_SIZE);

    private static UserVO makeUser(int i){
        UserVO userVO = new UserVO(i);
        userVO.setName("justin-" + i);
        return userVO;
    }

    //定义一个线程
    private static class ProducerWorker implements Runnable{
        private ProducerRecord<String, String> record;
        private KafkaProducer<String, String> producer;
        public ProducerWorker(ProducerRecord<String, String> record, KafkaProducer<String, String> producer){
            this.record = record;
            this.producer = producer;
        }
        @Override
        public void run() {
            String id = Thread.currentThread().getId() + "-" + System.identityHashCode(producer);
            try{
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(e != null){
                            e.printStackTrace();
                        }
                        if(null != recordMetadata){
                            System.out.println(String.format("偏移量：%s，分区：%s", recordMetadata.offset(), recordMetadata.partition()));
                        }
                    }
                });
                System.out.println(id + ":数据【" + record + "】已发送");
                countDownLatch.countDown();
            }catch (Exception ex){
                ex.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {

        KafkaProducer<String, String> producer = new KafkaProducer<String, String>
                (KafaConstant.producerConfig(StringSerializer.class,StringSerializer.class));
        try{
            for(int i = 0;i<MSG_SIZE;i++){
                UserVO userVO = makeUser(i);
                ProducerRecord<String, String> record = new ProducerRecord<>(KafaConstant.REBALANCE_TOPIC, null,
                        System.currentTimeMillis(), userVO.getId()+"", userVO.toString());
                executorService.submit(new ProducerWorker(record, producer));
                Thread.sleep(500);
            }
            countDownLatch.await();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            producer.close();
            executorService.shutdown();
        }

    }
}
