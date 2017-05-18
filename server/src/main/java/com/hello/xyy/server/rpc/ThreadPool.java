package com.hello.xyy.server.rpc;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/18
 */
public class ThreadPool {

    private static ThreadPoolExecutor threadPoolExecutor;

    public static synchronized ThreadPoolExecutor getPool() {
        if (threadPoolExecutor == null) {

            threadPoolExecutor = new ThreadPoolExecutor(16, 20, 60,
                                                        TimeUnit.SECONDS, new LinkedBlockingDeque<>(),
                                                        new DefaultThreadFactory(),
                                                        new RejectedPolicy());
        }

        return threadPoolExecutor;

    }

    public   void submit(Runnable runnable){

        threadPoolExecutor.submit(runnable);

    }
}
