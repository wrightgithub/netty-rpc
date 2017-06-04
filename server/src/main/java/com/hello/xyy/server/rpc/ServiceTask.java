package com.hello.xyy.server.rpc;

import java.lang.reflect.InvocationTargetException;

import com.hello.xyy.model.Request;
import com.hello.xyy.model.Response;
import io.netty.channel.ChannelHandlerContext;
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
 * @date 2017/05/17
 */
public class ServiceTask implements Runnable {
    private static final Logger logger = LoggerFactory.getLogger(ServiceTask.class);
    private ChannelHandlerContext ctx;
    private Object msg;

    public ServiceTask(ChannelHandlerContext ctx, Object msg) {
        this.ctx = ctx;
        this.msg = msg;
    }

    @Override
    public void run() {

        try {
            execute(ctx, msg);
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (Exception e) {
            logger.error("business thread[{}] execute error ", Thread.currentThread().getName(), e);
            e.printStackTrace();
        }

    }

    private void execute(ChannelHandlerContext ctx, Object msg)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Response response = new Response();

        try {
            System.out.println("recv :" + msg);
            if (!(msg instanceof Request)) {
                response.setSuccess(false);
                response.setMessage("request is not Request class");
            }
            Request request = (Request)msg;
            response.setMessageId(request.getMessageId());
            Object result = null;
            try {
                result = reflect(request);
                response.setResult(result);
            } catch (InvocationTargetException e) {
                response.setThrowable(e.getTargetException());
            }catch (Throwable e){
                response.setThrowable(e);
            }
            response.setSuccess(true);
            logger.info("invoke success : {}.{} ,response = {}", request.getServerClass(), request.getMethod(), response);
        } catch (Exception e) {
            logger.error("server exception request is {}", msg, e);
            response.setSuccess(false);
            response.setMessage("server exception :" + e.getMessage());
        }

        synchronized (msg) {
            ctx.writeAndFlush(response);
        }

    }

    private Object reflect(Request request)
        throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        Object serviceBean = RpcServiceRegisterListener.getBean(request.getServerClass());
        return MethodUtils.invokeMethod(serviceBean, request.getMethod(), request.getArgs());
    }

}
