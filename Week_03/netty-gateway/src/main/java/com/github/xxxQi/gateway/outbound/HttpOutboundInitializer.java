package com.github.xxxQi.gateway.outbound;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpRequestEncoder;
import io.netty.handler.codec.http.HttpResponseDecoder;

import java.net.URI;

/**
 * @author: Administrator
 * @description:
 */
public class HttpOutboundInitializer extends ChannelInitializer<SocketChannel> {
    private ChannelHandlerContext requestContext;
    private URI requestURI;

    public HttpOutboundInitializer(ChannelHandlerContext requestContext, URI requestURI) {
        this.requestContext = requestContext;
        this.requestURI = requestURI;
    }

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        socketChannel.pipeline().addLast(new HttpResponseDecoder());
        socketChannel.pipeline().addLast(new HttpRequestEncoder());
        socketChannel.pipeline().addLast(new HttpOutboundHandler(requestContext,requestURI));
    }
}
