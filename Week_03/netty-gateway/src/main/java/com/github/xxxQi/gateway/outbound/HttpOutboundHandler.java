package com.github.xxxQi.gateway.outbound;

import com.github.xxxQi.gateway.constant.Constant;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.*;
import io.netty.util.CharsetUtil;

import java.net.URI;
import java.util.Objects;

import static io.netty.handler.codec.http.HttpHeaderNames.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaderValues.KEEP_ALIVE;
import static io.netty.handler.codec.http.HttpResponseStatus.NO_CONTENT;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

public class HttpOutboundHandler extends ChannelInboundHandlerAdapter {

    private ChannelHandlerContext requestContext;
    private URI requestURI;

    public HttpOutboundHandler(ChannelHandlerContext requestContext, URI requestURI) {
        this.requestContext = requestContext;
        this.requestURI = requestURI;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) {
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_0, HttpMethod.GET, requestURI.toASCIIString());
        // 只有由网关转发设置请求头，服务才接收
        request.headers().add(Constant.NIO_HEADER,Constant.NETTY_SIGN);
        request.headers().add(HttpHeaderNames.CONNECTION, HttpHeaderValues.KEEP_ALIVE);
        request.headers().add(HttpHeaderNames.CONTENT_LENGTH, request.content().readableBytes());
        ctx.writeAndFlush(request);
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;
            ByteBuf buf = content.content();
            String stringMsg = buf.toString(CharsetUtil.UTF_8);
            FullHttpResponse response = null;
            try {
                response = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(stringMsg.getBytes()));
                response.headers().set("Content-Type", "application/json");
                response.headers().setInt("Content-Length", stringMsg.getBytes().length);
            } catch (Exception e) {
                response = new DefaultFullHttpResponse(HTTP_1_1, NO_CONTENT);
            }
            outboundResponse(this.requestContext, response);
        }
        if (msg instanceof FullHttpResponse) {
            outboundResponse(this.requestContext, (FullHttpResponse) msg);
        }
    }

    private void outboundResponse (ChannelHandlerContext ctx, FullHttpResponse response){
        if (ctx.channel().isActive()) {
            response.headers().set(CONNECTION, KEEP_ALIVE);
            ctx.writeAndFlush(response);
        } else {
            ctx.writeAndFlush(response).addListener(ChannelFutureListener.CLOSE);
        }
    }
}