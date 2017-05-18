package com.hello.xyy.client.handler;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import com.hello.xyy.model.Response;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
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
 * @date 2017/05/12
 */
public class ClientMessageSendHandler extends ChannelInboundHandlerAdapter {

    private static final Logger logger = LoggerFactory.getLogger(ClientMessageSendHandler.class);

    private static Map<String, RpcCallBack> callBackMap = new ConcurrentHashMap<>(10);

    private Channel channel;

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
        // 此处无业务逻辑，所以单线程执行即可
        if (!(msg instanceof Response)) {
            logger.error("msg can not cast Response ,msg is {}", msg);
        }
        Response response = (Response)msg;
        if (!response.isSuccess()) {
            logger.error("server occur exception,response msg is {} ", response.getMessage());
        }
        logger.info("response is {}", response);
        RpcCallBack callBack = callBackMap.get(response.getMessageId());
        callBack.notifyWait(response);

    }

    public void send(RpcCallBack callBack) {
        callBackMap.put(callBack.getRequest().getMessageId(), callBack);
        channel.writeAndFlush(callBack.getRequest());
    }

}
