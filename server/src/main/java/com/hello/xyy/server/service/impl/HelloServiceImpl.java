package com.hello.xyy.server.service.impl;

import java.util.List;

import com.hello.xyy.annotation.RpcProvider;
import com.hello.xyy.api.HelloService;
import com.hello.xyy.api.exception.HelloException;
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

        try {
            List list = null;
            list.size();
        } catch (Exception e) {
            throw new HelloException("fuck this occur exception",e);
        }
        return "hello world!";
    }

    @Override
    @ManagedOperation
    public String sayHello(String title, Long orderId) {
        return title + " " + orderId;
    }

}
