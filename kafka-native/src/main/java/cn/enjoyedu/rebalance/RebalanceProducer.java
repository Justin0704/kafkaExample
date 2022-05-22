package cn.enjoyedu.rebalance;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 分区再均衡，多线程下的生产者
 */
public class RebalanceProducer {

    private static final int MSG_SIZE = 50;
    //定义一个线程池
    private static ExecutorService executorService = Executors.newFixedThreadPool(Runtime.getRuntime().availableProcessors());



    public static void main(String[] args) {


    }
}
