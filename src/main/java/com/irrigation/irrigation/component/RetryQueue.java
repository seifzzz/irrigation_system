package com.irrigation.irrigation.component;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.Queue;


//This class used to store failed process

@Component
public class RetryQueue {
    private Queue<Long> queue = new LinkedList<>();

    public Queue<Long> getQueue() {
        return queue;
    }

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

