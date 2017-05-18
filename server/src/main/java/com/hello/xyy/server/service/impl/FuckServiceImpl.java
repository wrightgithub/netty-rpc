package com.hello.xyy.server.service.impl;

import com.hello.xyy.annotation.RpcProvider;
import com.hello.xyy.api.FuckService;

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
@RpcProvider(serviceInterface =FuckService.class )
public class FuckServiceImpl implements FuckService {
    @Override
    public void fuck() {
        System.out.println("say fuck ........");
    }
}
