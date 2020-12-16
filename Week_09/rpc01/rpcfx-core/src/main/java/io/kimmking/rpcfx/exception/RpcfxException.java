package io.kimmking.rpcfx.exception;

/**
 * @author: Administrator
 * @description:
 */
public class RpcfxException extends RuntimeException{

    public RpcfxException(String message) {
        super(message);
    }

    public RpcfxException(String message, Throwable cause) {
        super(message, cause);
    }
}
