package com.hello.xyy.jmx;

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
public interface HelloMBean {
    String getName();

    void setName(String name);

    void printHello();

    void printHello(String whoName);
}