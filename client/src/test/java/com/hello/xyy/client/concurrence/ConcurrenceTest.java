package com.hello.xyy.client.concurrence;

import java.util.concurrent.CountDownLatch;

import ch.qos.logback.core.net.server.Client;
import com.hello.xyy.client.consumer.ConsumerService2;
import org.apache.commons.lang3.time.StopWatch;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConcurrenceTest {

    @Autowired
    private ConsumerService2 consumerService2;

    @Test
    public void test() throws InterruptedException {
        int num=2000;


        //开始计时
        StopWatch sw = new StopWatch();
        sw.start();

        CountDownLatch signal = new CountDownLatch(1);
        CountDownLatch finish = new CountDownLatch(num);

        for (int i = 0; i < num; i++) {
            new Thread(new ClientTask(consumerService2,signal,finish)).start();
        }

        signal.countDown();
        finish.await();

        sw.stop();

        System.out.printf("耗时: %s ms",sw.getTime());
        System.out.println();

    }

}
