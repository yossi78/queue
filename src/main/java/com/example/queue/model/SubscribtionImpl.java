package com.example.queue.model;

import java.util.concurrent.Flow;

public class SubscribtionImpl implements Flow.Subscription {
    public Long size=1L;

    @Override
    public void request(long n) {
        this.size=n;

    }

    @Override
    public void cancel() {

    }
}
