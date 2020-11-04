package com.github.xxxQi.gateway.filter;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.http.FullHttpRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: Administrator
 * @description:
 */
public class HttpRequestFilterHandler implements HttpRequestFilter{
    /**
     * Request过滤操作链
     */
    List<Filter> rquestFilters = new ArrayList<>();
    /**
     * Response过滤操作链
     */
    List<Filter> responseFilters = new ArrayList<>();

    /**
     * 过滤器：把所有网络请求在经过服务的时候追加header头或者其他操作
     * @param fullRequest
     * @param ctx
     */
    @Override
    public void filter(FullHttpRequest fullRequest, ChannelHandlerContext ctx) {
        for (Filter filter : rquestFilters) {
            filter.handle(fullRequest);
        }
    }
}
