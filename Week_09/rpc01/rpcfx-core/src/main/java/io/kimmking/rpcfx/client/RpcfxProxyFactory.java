package io.kimmking.rpcfx.client;

import lombok.SneakyThrows;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.implementation.InvocationHandlerAdapter;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: Administrator
 * @description:
 */
public class RpcfxProxyFactory {
    private static final Map<String, Object> cache = new ConcurrentHashMap<>();
    private static final RpcfxProxyFactory instance = new RpcfxProxyFactory();

    public RpcfxProxyFactory() {
    }

    public static RpcfxProxyFactory getInstance() {
        return instance;
    }

    @SneakyThrows
    public <T> T create(Class<T> serviceClass, String url) {
        // 0. 替换动态代理 -> AOP
//        return (T) Proxy.newProxyInstance(Rpcfx.class.getClassLoader(), new Class[]{serviceClass}, new RpcfxInvocationHandler(serviceClass, url));
        if (cache.containsKey(serviceClass.getName())) {
            return (T) cache.get(serviceClass.getName());
        } else {
            T result = (T) new ByteBuddy().subclass(Object.class)
                    .implement(serviceClass)
                    .intercept(InvocationHandlerAdapter.of(new RpcfxInvocationHandler(serviceClass, url)))
                    .make()
                    .load(ClassLoader.getSystemClassLoader())
                    .getLoaded()
                    .getDeclaredConstructor()
                    .newInstance();
            cache.putIfAbsent(serviceClass.getName(), result);
            return result;
        }

    }
}
