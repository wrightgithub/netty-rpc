package com.hello.xyy.client.consumer;

import com.hello.xyy.annotation.RpcConsumer;
import com.hello.xyy.api.HelloService;
import com.hello.xyy.api.exception.HelloException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
    private static final Logger logger = LoggerFactory.getLogger(ConsumerService.class);

    @RpcConsumer
    private HelloService helloService;

    public void test() {

        try {
            helloService.sayHello();
        } catch (HelloException e) {
            logger.error("occur HelloException", e);
        } catch (RuntimeException r) {
            logger.error("occur RuntimeException", r);
            System.out.println(r.getCause());
        } catch (Exception e) {
            logger.error("occur Exception", e);
        }
    }

    public void test2() {

        String result = helloService.sayHello("title", 1234L);
        System.out.println("ConsumerService's result = " + result);
    }
}
