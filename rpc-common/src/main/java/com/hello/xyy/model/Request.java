package com.hello.xyy.model;

import java.io.Serializable;

import lombok.Data;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 *
 * @author lihao
 * @date 2017/05/07
 */
@Data
public class Request implements Serializable {
    private String messageId;
    private String serverClass;
    private String method;
    private Class<?>[] parameterTypes;
    private Object[] args;
}
