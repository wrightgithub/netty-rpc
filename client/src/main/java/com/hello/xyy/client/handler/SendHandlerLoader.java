package com.hello.xyy.client.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/13
 */
public class SendHandlerLoader {
    private static Map<String, ClientMessageSendHandler> handlerMap = new ConcurrentHashMap<>(10);
    private static final Lock lock = new ReentrantLock();
    private static final Condition condition = lock.newCondition();

    public static ClientMessageSendHandler getHandler(String key) throws InterruptedException {
        lock.lock();
        try {
            while (handlerMap.get(key) == null) {
                condition.await();
            }
        } finally {
            lock.unlock();
        }
        return handlerMap.get(key);
    }

    public static void put(String interFaceName, ClientMessageSendHandler sendHandler) {
        handlerMap.putIfAbsent(interFaceName, sendHandler);
        lock.lock();
        try {
            condition.signalAll();
        } finally {
            lock.unlock();
        }
    }

}
