package com.hello.xyy.server.handler;

import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.RejectedExecutionHandler;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import com.hello.xyy.model.Request;
import com.hello.xyy.model.Response;
import com.hello.xyy.server.rpc.DefaultThreadFactory;
import com.hello.xyy.server.rpc.RejectedPolicy;
import com.hello.xyy.server.rpc.RpcServiceRegisterListener;
import com.hello.xyy.server.rpc.ServiceTask;
import com.hello.xyy.server.rpc.ThreadPool;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import org.apache.commons.lang3.reflect.MethodUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class ServerMessageRecvHandler extends ChannelInboundHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(ServerMessageRecvHandler.class);

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {

        // 业务线池
        ThreadPool.getPool().submit(new ServiceTask(ctx, msg));
    }

}
