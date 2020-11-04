package com.github.xxxQi.gateway.inbound;

import com.github.xxxQi.gateway.filter.HttpRequestFilterHandler;
import com.github.xxxQi.gateway.outbound.HttpOutboundClient;
import com.github.xxxQi.gateway.router.HttpEndpointRouterHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.util.ReferenceCountUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

public class HttpInboundHandler extends ChannelInboundHandlerAdapter {

    private final HttpEndpointRouterHandler routerHandler = new HttpEndpointRouterHandler();
    private final HttpRequestFilterHandler filterHandler = new HttpRequestFilterHandler();
    private final HttpOutboundClient outboundClient = new HttpOutboundClient();


    private static Logger logger = LoggerFactory.getLogger(HttpInboundHandler.class);
    private final String proxyServer;

    public HttpInboundHandler(String proxyServer) {
        this.proxyServer = proxyServer;
    }
    
    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        try {
            logger.info("微型网关接收到数据 : {}",msg);
            if (Objects.isNull(msg) || !(msg instanceof FullHttpRequest)){
                //拒绝请求操作
            }
            //处理http 请求
            handleHttpRequest(ctx,msg);
        } catch(Exception e) {
            e.printStackTrace();
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    private void handleHttpRequest(ChannelHandlerContext ctx, Object msg) {
        FullHttpRequest fullRequest = (FullHttpRequest) msg;
        //执行请求过滤
        filterHandler.filter(fullRequest,ctx);
        //根据权重随机分配route
        String url = routerHandler.route();
        //转发请求
        outboundClient.send(ctx,url);
    }

}
