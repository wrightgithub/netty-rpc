package com.hello.xyy.client.rpc;

import java.util.concurrent.TimeUnit;

import com.hello.xyy.client.handler.ClientMessageSendHandler;
import com.hello.xyy.client.handler.SendHandlerLoader;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoop;
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
import org.springframework.util.Assert;
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
public class ClientStart {
    private static final Logger logger = LoggerFactory.getLogger(RpcConsumerBeanProcessor.class);
    public static final String DELIMITER = ":";

    public static void startClient(String ipAddr, String interFaceName) {

        Assert.notNull(ipAddr);
        Assert.notNull(interFaceName);
        String[] strs = StringUtils.split(ipAddr, DELIMITER);
        String ip = strs[0];
        int port = Integer.parseInt(strs[1]);
        EventLoopGroup group = new NioEventLoopGroup();
        try {

            ClientMessageSendHandler clientMessageSendHandler = new ClientMessageSendHandler();
            Bootstrap b = new Bootstrap();
            b.group(group)
             .channel(NioSocketChannel.class)
             .handler(new ChannelInitializer<SocketChannel>() {
                 @Override
                 public void initChannel(SocketChannel ch) throws Exception {
                     ChannelPipeline p = ch.pipeline();
                     // add 编码器  头4个字节代表消息长度
                     p.addLast(new LengthFieldPrepender(4));
                     p.addLast(new ObjectEncoder());
                     // add 解码器
                     p.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                     p.addLast(new ObjectDecoder(
                         ClassResolvers.weakCachingConcurrentResolver(this.getClass().getClassLoader())));
                     p.addLast(clientMessageSendHandler);

                 }
             });
            b.connect(ip, port).addListener((ChannelFutureListener)channelFuture -> {
                // 连接重试
                if (channelFuture.isSuccess()) {
                    SendHandlerLoader.put(interFaceName, clientMessageSendHandler);
                } else {
                    channelFuture.channel().eventLoop().schedule(() -> {
                        System.out.println("NettyRPC server is down,start to reconnecting to: " + ipAddr);
                        startClient(ipAddr, interFaceName);
                    }, 2, TimeUnit.SECONDS);
                }
            });

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        // todo close 需要有一个专门的地方控制
    }
}
