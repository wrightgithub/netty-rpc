package com.hello.xyy.server.rpc;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import com.hello.xyy.annotation.RpcProvider;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

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
@Component
public class RpcServiceRegisterListener implements ApplicationContextAware {

    private static Map<String, Object> serviceMap = new ConcurrentHashMap<>();


    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, Object> beanMap = applicationContext.getBeansWithAnnotation(RpcProvider.class);
        beanMap.forEach((name,bean)->{
            RpcProvider rpcProvider= bean.getClass().getAnnotation(RpcProvider.class);
            serviceMap.put(rpcProvider.serviceInterface().getName(),bean);
        });
        printRegisteredService();
    }

    private void printRegisteredService() {
        System.out.println("-------------注册的服务--------------");
        serviceMap.forEach((k,v)-> System.out.println(k));
        System.out.println("------------------------------------");
    }

    public  static Object  getBean(String interfaceName){
        return serviceMap.get(interfaceName);
    }
}
