package com.hello.xyy.client.concurrence;

import java.util.concurrent.CountDownLatch;

import com.hello.xyy.client.consumer.ConsumerService2;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/18
 */
public class ClientTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ClientTask.class);
    private ConsumerService2 consumerService2;
    private CountDownLatch signal;
    private CountDownLatch finish;

    public ClientTask(ConsumerService2 consumerService2, CountDownLatch signal, CountDownLatch finish) {
        this.consumerService2 = consumerService2;
        this.signal = signal;
        this.finish = finish;
    }

    @Override
    public void run() {

        try {
            signal.await();
            String result = consumerService2.test2();
            System.out.println(Thread.currentThread().getName() + " is ok ,result is " + result);
            finish.countDown();

        } catch (Exception e) {
            System.out.println(Thread.currentThread().getName() + " error");
            logger.error(e.getMessage(), e);
        }
    }
}
