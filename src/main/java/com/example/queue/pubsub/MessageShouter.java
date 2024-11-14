package com.example.queue.pubsub;

import com.example.queue.model.ImportantMessage;
import com.example.queue.model.SubscribtionImpl;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.Flow.Publisher;
import java.util.concurrent.Flow.Subscriber;

public class MessageShouter implements Publisher<ImportantMessage> {

	private Map<Subscriber, SubscribtionImpl> subscribers = new HashMap<>();

	@Override
	public void subscribe(Subscriber<? super ImportantMessage> subscriber) {
		SubscribtionImpl subscriptionImpl =new SubscribtionImpl();
		subscriber.onSubscribe(subscriptionImpl);
		subscribers.put(subscriber,subscriptionImpl);
	}


	public void shout(ImportantMessage message) {
		System.out.println("Shouting: " + message);
		for(Subscriber sbscriber:subscribers.keySet() ){
			SubscribtionImpl subscribtionImpl =subscribers.get(sbscriber);
			runOnNext(sbscriber,subscribtionImpl,message);
		}
	}




	private void runOnNext(Subscriber subscriber,SubscribtionImpl  subscriberImpl , ImportantMessage message){
		try{
			if(subscriberImpl.size>0){
				subscriber.onNext(message);
				subscriberImpl.size =subscriberImpl.size-1;
			}
		}catch (Exception e){
			System.out.println("Failed to Shouting: " + message);
			subscriber.onError(e);
		}
	}

	public void close() throws InterruptedException {
		System.out.println("Shouter complete");
		Thread.sleep(1000);
		for(Subscriber sbscriber:subscribers.keySet() ){
			sbscriber.onComplete();;
		}
	}

}
