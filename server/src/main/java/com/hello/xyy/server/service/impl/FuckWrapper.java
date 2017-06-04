package com.hello.xyy.server.service.impl;

import org.springframework.jmx.export.annotation.ManagedOperation;
import org.springframework.jmx.export.annotation.ManagedResource;
import org.springframework.stereotype.Component;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/23
 */
@ManagedResource
@Component
public class FuckWrapper {

    @ManagedOperation
    public String test(){
        return "fuck";
    }
}
