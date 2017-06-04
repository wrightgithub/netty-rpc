package com.hello.xyy.client.rpc;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.UUID;

import com.hello.xyy.client.handler.ClientMessageSendHandler;
import com.hello.xyy.client.handler.RpcCallBack;
import com.hello.xyy.client.handler.SendHandlerLoader;
import com.hello.xyy.model.Request;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/12
 */
public class ConsumerProxy implements InvocationHandler {

    private String interFaceName;
    private String ipAddr;

    public ConsumerProxy(String ipAddr,String interFaceName) {
        this.interFaceName = interFaceName;
        this.ipAddr = ipAddr;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        ClientStart.startClient(ipAddr, interFaceName);

        Request request = new Request();
        request.setMessageId(UUID.randomUUID().toString());
        request.setMethod(method.getName());
        String interFaceName = method.getDeclaringClass().getName();
        request.setServerClass(interFaceName);
        request.setParameterTypes(method.getParameterTypes());
        request.setArgs(args);
        RpcCallBack rpcCallBack = new RpcCallBack(request);
        SendHandlerLoader.getHandler(interFaceName).send(rpcCallBack);
        return rpcCallBack.waitResponse();
    }

    public Object getInterFaceName() {
        return interFaceName;
    }

    public void setInterFaceName(String interFaceName) {
        this.interFaceName = interFaceName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }
}
