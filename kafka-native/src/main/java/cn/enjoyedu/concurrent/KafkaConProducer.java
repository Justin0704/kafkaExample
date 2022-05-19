package cn.enjoyedu.concurrent;

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
 * 多线程下的生产者
 */
public class KafkaConProducer {

    //发送消息个数
    private static final int MSG_SIZE = 100;
    //负责发送消息的线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());
    //
    private static CountDownLatch countDownLatch = new CountDownLatch(MSG_SIZE);
    //一个用户对象
    private static UserVO makeUser(int id){
        UserVO userVO = new UserVO(id);
        String userName = "笔记本" + id;
        userVO.setName(userName);
        return userVO;
    }
    /**
     * 发送消息的任务
     */
    private static class ProducerWorker implements Runnable{

        private ProducerRecord<String, String> record;
        private KafkaProducer<String, String> producer;

        public ProducerWorker(ProducerRecord<String, String> record,
                              KafkaProducer<String, String> producer){
            this.producer = producer;
            this.record = record;
        }
        @Override
        public void run() {
            final String id = Thread.currentThread().getId() + " - " + System.identityHashCode(producer);
            try{
                producer.send(record, new Callback() {
                    @Override
                    public void onCompletion(RecordMetadata recordMetadata, Exception e) {
                        if(e != null){
                            e.printStackTrace();
                        }
                        if(recordMetadata != null){
                            System.out.println(id + " | " + String.format("偏移量：%s，分区：%s",
                                    recordMetadata.offset(), recordMetadata.partition()));
                        }
                    }
                });
                System.out.println(id + "数据：【" + record + "】已发送");
                countDownLatch.countDown();
            }catch(Exception e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        KafkaProducer<String, String> producer = new KafkaProducer<String, String>(
                KafaConstant.producerConfig(StringSerializer.class, StringSerializer.class));
        try{
            //循环发送，通过线程池的方式
            for(int i = 0;i<MSG_SIZE; i++){
                UserVO userVO = new UserVO(i);
                ProducerRecord<String, String> record = new ProducerRecord<>(
                        KafaConstant.CONCURRENT_USER_INFO_TOPIC,
                        null,
                        System.currentTimeMillis(),
                        userVO.getId()+"",
                        userVO.toString());
                executorService.submit(new ProducerWorker(record, producer));
            }
            countDownLatch.await();
        }catch (Exception ex){
            ex.printStackTrace();
        }finally {
            producer.close();
            executorService.shutdown();
        }

    }
}
