package com.hello.xyy.client;

import com.hello.xyy.client.consumer.ConsumerService;
import com.hello.xyy.client.consumer.ConsumerService2;
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
 * @date 2017/05/13
 */

@RunWith(SpringRunner.class)
@SpringBootTest
public class ConsumerTest {

    @Autowired
    private ConsumerService consumerService;

    @Autowired
    private ConsumerService2 consumerService2;

    @Test
    public void test() {
        consumerService.test();

    }

    @Test
    public void test2(){
        consumerService.test2();
        consumerService2.test2();
    }

}
