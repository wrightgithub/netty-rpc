package com.hello.xyy.server.rpc;

import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadPoolExecutor;

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
public class RejectedPolicy implements RejectedExecutionHandler {
    public RejectedPolicy() {
    }

    @Override
    public void rejectedExecution(Runnable r, ThreadPoolExecutor executor) {

    }
}
