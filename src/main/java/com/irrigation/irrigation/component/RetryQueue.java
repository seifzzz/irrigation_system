package com.irrigation.irrigation.model;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;


public class retryQueue {
    private Queue<Long> queue = new LinkedList<>();

    public void add(Long plotId) {
        queue.add(plotId);
    }

    public Long poll() {
        return queue.poll();
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}

