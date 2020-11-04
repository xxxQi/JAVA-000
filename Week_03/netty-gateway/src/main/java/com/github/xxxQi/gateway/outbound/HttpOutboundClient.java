package com.github.xxxQi.gateway.outbound;

import com.github.xxxQi.gateway.inbound.HttpInboundInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.URI;
import java.net.URISyntaxException;

/**
 * @author: Administrator
 * @description:
 */
public class HttpOutboundClient {

    public void send(ChannelHandlerContext ctx, String url)  {
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            URI uri = new URI(url);
            Bootstrap b = new Bootstrap()
                    .group(workerGroup)
                    .option(ChannelOption.SO_KEEPALIVE, true)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 3000)
                    .channel(NioSocketChannel.class)
                    .handler(new HttpOutboundInitializer(ctx, uri));
            ChannelFuture future = b.connect(uri.getHost(), uri.getPort()).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException | URISyntaxException e) {
            e.printStackTrace();
        } finally {
            workerGroup.shutdownGracefully();
        }

    }
}
