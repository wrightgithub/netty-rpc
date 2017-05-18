package com.hello.xyy.client.rpc;

import java.lang.reflect.Proxy;

import com.hello.xyy.client.utils.PrintUtils;
import org.springframework.beans.factory.FactoryBean;

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

public class RpcConsumerFactoryBean implements FactoryBean {

    private Object interFaceName;
    private String ipAddr;

    @Override
    public Object getObject() throws Exception {
        //Reflection.newProxy(getObjectType(),new ConsumerProxy());
        // 单例模式，相同接口只会生成一个实例。
        PrintUtils.show("生成bean实例", String.valueOf(getObjectType()));
        return Proxy.newProxyInstance(getObjectType().getClassLoader(),
                                      new Class[] {getObjectType()},
                                      new ConsumerProxy());

    }

    @Override
    public Class<?> getObjectType() {
        if (interFaceName == null) {
            return null;
        }
        return (Class<?>)interFaceName;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    public Object getInterFaceName() {
        return interFaceName;
    }

    public void setInterFaceName(Object interFaceName) {
        this.interFaceName = interFaceName;
    }

    public String getIpAddr() {
        return ipAddr;
    }

    public void setIpAddr(String ipAddr) {
        this.ipAddr = ipAddr;
    }

}
