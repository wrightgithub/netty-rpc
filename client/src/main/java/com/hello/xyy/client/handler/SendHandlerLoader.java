package com.hello.xyy.client.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;


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

    public static ClientMessageSendHandler getHandler(String key) {
        return handlerMap.get(key);
    }

    public static void put(String interFaceName, ClientMessageSendHandler sendHandler){
        handlerMap.putIfAbsent(interFaceName,sendHandler);
    }

}
