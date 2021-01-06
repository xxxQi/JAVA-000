package com.example.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPubSub;

import java.util.UUID;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author: Administrator
 * @description:
 */
public class RedisExample {
    private static final ReentrantLock lock = new ReentrantLock();

    public static void main(String[] args) throws InterruptedException {
//        distributedLock();
        countdown();
//        pubSub();
    }

    /**
     * 计数器，模拟减库存
     */
    private static void countdown() {
        Jedis jedis2 = new Jedis("redis://p:123456@127.0.0.1:6379");
        jedis2.set("count-down", "10000");
        ExecutorService threadPool = Executors.newFixedThreadPool(100);
        for (int i = 0; i < 10000; i++) {
            threadPool.execute(()->{
                Jedis jedis = new Jedis("redis://p:123456@127.0.0.1:6379");
                String threadName = Thread.currentThread().getName();
                Long decr = jedis.decr("count-down");
                if (decr < 0) {
                    System.out.println(threadName+"扣库存失败！！！"+decr);
                } else {
                    System.out.println(threadName+"扣库存成功"+decr);
                }
            });
        }
        threadPool.shutdown();
    }


    /**
     * 分布式锁
     */
    public static void distributedLock() {
        Jedis jedis = new Jedis("redis://p:123456@127.0.0.1:6379");
        try {
            lock.lock();
            String lockResult = jedis.set("lock", UUID.randomUUID().toString(), "NX", "EX", 10L);
            if ("OK".equals(lockResult)) {
                System.out.println("获取锁成功");
            } else {
                System.out.println("获取锁失败");
            }
        } catch (Exception e) {
            System.out.println("获取锁异常");
            e.printStackTrace();
        } finally {
            lock.unlock();
        }
    }

    public static void pubSub() throws InterruptedException {
        final String ch = "myChannel";
        //订阅者
        Thread sub = new Thread(() -> {
            Jedis jedis = new Jedis("redis://p:123456@127.0.0.1:6379");
            jedis.subscribe(new Subscriber(), ch);
        });
        //发布者
        Thread pub = new Thread(() -> {
            Jedis jedis = new Jedis("redis://p:123456@127.0.0.1:6379");
            for (int i = 0; i < 10; i++) {
                jedis.publish(ch, "message" + i);
                try {
                    Thread.sleep(1000L);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
        sub.start();
        pub.start();
        pub.join();
    }

    public static class Subscriber extends JedisPubSub {
        public Subscriber() {
        }

        public void onMessage(String channel, String message) {
            System.out.println(String.format("receive redis published message, channel %s, message %s", channel, message));
        }

        public void onSubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("subscribe redis channel success, channel %s, subscribedChannels %d",
                    channel, subscribedChannels));
        }

        public void onUnsubscribe(String channel, int subscribedChannels) {
            System.out.println(String.format("unsubscribe redis channel, channel %s, subscribedChannels %d",
                    channel, subscribedChannels));
        }
    }
}
