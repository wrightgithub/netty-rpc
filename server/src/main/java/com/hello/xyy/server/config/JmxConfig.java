package com.hello.xyy.server.config;

import javax.management.InstanceAlreadyExistsException;
import javax.management.MBeanRegistrationException;
import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.MalformedObjectNameException;
import javax.management.NotCompliantMBeanException;
import javax.management.ObjectName;

import com.sun.jdmk.comm.HtmlAdaptorServer;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.EnableMBeanExport;
import org.springframework.context.annotation.Primary;
import org.springframework.jmx.export.MBeanExporter;
import org.springframework.jmx.export.annotation.AnnotationMBeanExporter;
import org.springframework.jmx.support.ConnectorServerFactoryBean;
import org.springframework.jmx.support.MBeanServerFactoryBean;
import org.springframework.remoting.rmi.RmiRegistryFactoryBean;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/21
 */

@Configuration
@EnableMBeanExport(server = "mBeanServer")
public class JmxConfig {

    @Bean
    public MBeanServer mBeanServer() throws Exception {
        ObjectName adapterName = new ObjectName("" + ":name=htmladapter,port=8082");
        System.err.println("web jmx  console : http://localhost:8082");
        HtmlAdaptorServer adapter = new HtmlAdaptorServer();
        adapter.start();
        MBeanServer mBeanServer = MBeanServerFactory.createMBeanServer();
        mBeanServer.registerMBean(adapter, adapterName);
        return mBeanServer;
    }

}
