package com.hello.xyy.server.service.impl;

import com.hello.xyy.api.HelloService;
import com.hello.xyy.annotation.RpcProvider;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/07
 */

@RpcProvider(serviceInterface = HelloService.class)
public class HelloServiceImpl implements HelloService {
    @Override
    public String sayHello() {
        return "hello world!";
    }

    @Override
    public String sayHello(String title, Long orderId) {
        return title + " " + orderId;
    }
}
