package com.example.queue.pubsub;

import com.example.queue.model.SubscribtionImpl;
import com.example.queue.model.ImportantMessage;

import java.util.concurrent.Flow.Subscriber;
import java.util.concurrent.Flow.Subscription;

public class MessageListener implements Subscriber<ImportantMessage> {

	private final int id;
	public SubscribtionImpl subscriptionImpl;

	private Long size;

	public MessageListener(int id) {
		this.id = id;
	}

	@Override
	public void onSubscribe(Subscription subscription) {
		System.out.println("Listener " + id + " successfully subscribed");
		this.subscriptionImpl = (SubscribtionImpl) subscription;
		subscription.request(1);
		this.size=1L;
	}

	@Override
	public void onNext(ImportantMessage item) {
		System.out.println("Listener " + id + ": " + item);
	}

	@Override
	public void onError(Throwable throwable) {
		System.out.println("Listener " + id + " Error: " + throwable.getMessage());
	}

	@Override
	public void onComplete() {
		System.out.println("Listener " + id + " Completed");
	}

	public void mute() {
		this.size = this.subscriptionImpl.size;
		subscriptionImpl.request(0);
	}

	public void unmute() {
		subscriptionImpl.request(this.size);
	}
}
