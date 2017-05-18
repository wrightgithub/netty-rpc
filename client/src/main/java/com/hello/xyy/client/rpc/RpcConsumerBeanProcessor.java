package com.hello.xyy.client.rpc;

import java.lang.reflect.Field;
import java.util.LinkedHashMap;
import java.util.Map;

import com.hello.xyy.annotation.RpcConsumer;
import com.hello.xyy.client.handler.ClientMessageSendHandler;
import com.hello.xyy.client.utils.PrintUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ConfigurationClassPostProcessor;
import org.springframework.core.Conventions;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;
import org.springframework.util.ClassUtils;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.ReflectionUtils.FieldCallback;
import org.springframework.util.StringUtils;

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
@Component
public class RpcConsumerBeanProcessor implements BeanFactoryPostProcessor, BeanClassLoaderAware {
    private static final Logger logger = LoggerFactory.getLogger(RpcConsumerBeanProcessor.class);
    public static final String IpAddr = "127.0.0.1:8080";
    private ClassLoader classLoader;
    private static final String CONFIGURATION_CLASS_ATTRIBUTE = Conventions.getQualifiedAttributeName(
        ConfigurationClassPostProcessor.class,
        "configurationClass");

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {

        for (String name : beanFactory.getBeanDefinitionNames()) {
            BeanDefinition beanDefinition = beanFactory.getBeanDefinition(name);

            if (beanDefinition.getAttribute(CONFIGURATION_CLASS_ATTRIBUTE) == null) {
                continue;
            }

            Class<?> clazz = ClassUtils.resolveClassName(beanDefinition.getBeanClassName(), this.classLoader);

            ReflectionUtils.doWithFields(clazz, field -> {
                RpcConsumer rpcConsumer = AnnotationUtils.getAnnotation(field, RpcConsumer.class);
                if (rpcConsumer == null) {
                    return;
                }

                // 此处生成一个 rootBeanDefinition 在factorybean中会对其生成一个bean实例对象。
                // 如果不适用工厂方法生成对象，会报bean 对是接口的，会报错。
                RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
                rootBeanDefinition.setBeanClass(RpcConsumerFactoryBean.class);
                rootBeanDefinition.setLazyInit(false);
                // 此处的两个参数，会spring会在factorybean中去取对应的变量
                rootBeanDefinition.getPropertyValues().addPropertyValue("interFaceName", field.getType());
                rootBeanDefinition.getPropertyValues().addPropertyValue("ipAddr", IpAddr);

                BeanDefinitionRegistry registry = (BeanDefinitionRegistry)beanFactory;
                registry.registerBeanDefinition(field.getName(), rootBeanDefinition);

                ClientStart.startClient(IpAddr, field.getType().getName());

            });

        }
    }

    @Override
    public void setBeanClassLoader(ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

}
