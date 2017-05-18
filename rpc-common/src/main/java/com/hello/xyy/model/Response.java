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
public class Response implements Serializable {
    private String messageId;
    private boolean success;
    private String message;
    private Object result;
}
