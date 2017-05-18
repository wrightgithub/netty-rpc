package com.hello.xyy.client.consumer;

import com.hello.xyy.annotation.RpcConsumer;
import com.hello.xyy.api.HelloService;
import org.springframework.stereotype.Component;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/11
 */
@Component
public class ConsumerService {

    @RpcConsumer
    private HelloService helloService;

    public void test() {

        helloService.sayHello();
    }

    public void test2() {

        String result = helloService.sayHello("title", 1234L);
        System.out.println("ConsumerService's result = " + result);
    }
}
