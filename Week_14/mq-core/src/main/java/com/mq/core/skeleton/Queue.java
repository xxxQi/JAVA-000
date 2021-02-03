package com.mq.core.skeleton;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class Queue {
    /**
     * 各个分组的读取位置记录
     */
    private Map<String, AtomicInteger> offset = new HashMap<>();

    /**
     * 写位置记录
     */
    private int writeIndex = 0;
    /**
     * 存储队列
     */
    private final List<String> queue = new ArrayList<>();

    public void put(String message) {
        queue.add(message);
        writeIndex += 1;
    }

    public String get(String group) {
        int index = offset.getOrDefault(group, new AtomicInteger(-1)).incrementAndGet();
        if (writeIndex == 0 || index >= queue.size()) {
            return null;
        }
        return queue.get(index);
    }

    public boolean isEmpty() {
        return writeIndex == 0 || writeIndex >= queue.size();
    }

    public int size() {
        return writeIndex;
    }
}
