package com.hello.xyy.server.service.impl;

import com.hello.xyy.api.HelloService;
import com.hello.xyy.annotation.RpcProvider;
import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;

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
@ManagedResource
public class HelloServiceImpl implements HelloService {

    @Override
    @ManagedOperation
    public String sayHello() {
        return "hello world!";
    }

    @Override
    @ManagedOperation
    public String sayHello(String title, Long orderId) {
        return title + " " + orderId;
    }
}
