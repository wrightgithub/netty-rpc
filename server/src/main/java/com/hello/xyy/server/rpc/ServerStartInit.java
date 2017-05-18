package com.hello.xyy.server.rpc;

import com.hello.xyy.server.handler.ServerMessageRecvHandler;
import com.hello.xyy.server.utils.PrintUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * +----------------------------+
 * |                            |
 * |   God bless this code      |
 * |                            |
 * +----------------------------+
 * netty启动
 *
 * @author lihao
 * @date 2017/05/11
 */

@Component
public class ServerStartInit implements InitializingBean {
    @Override
    public void afterPropertiesSet() throws Exception {

        // SO_BACKLOG 最大连接数数
        // SO_KEEPALIVE 心跳检测
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup(16);
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup)
             .channel(NioServerSocketChannel.class)
             .option(ChannelOption.SO_BACKLOG, 128)
             .childOption(ChannelOption.SO_KEEPALIVE, true)
             .handler(new LoggingHandler(LogLevel.INFO))
             .childHandler(new ChannelInitializer<SocketChannel>() {
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
                     p.addLast(new ServerMessageRecvHandler());

                 }
             });

            int inetPort = 8080;
            String inetHost = "127.0.0.1";
            b.bind(inetHost, inetPort).sync()
             .addListener(new ChannelFutureListener() {
                @Override
                public void operationComplete(ChannelFuture future) throws Exception {
                    if(future.isSuccess()){
                        String info="ip:"+inetHost+"\n"+"port:"+inetPort;
                        PrintUtils.show("服务端信息",info);
                    }
                }
            });

        } finally {
            //bossGroup.shutdownGracefully();
            //workerGroup.shutdownGracefully();
        }
    }
}
