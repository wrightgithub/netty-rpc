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
 * @date 2017/05/14
 */
@Component
public class ConsumerService2 {

    @RpcConsumer
    private HelloService helloService;


    public String test2() {

        String result = helloService.sayHello("title", 1234L);
        return result;
    }
}
