package com.cjs.goHead.observer;

import java.util.ArrayList;
import java.util.List;

public class MyTopic implements Subject{
	/**
	 * 	When subject is updated,it will notify all the observers and every observer will update.
	 * 	In update process of observer,it can consume the update message which are stored in Subject because there 
	 * exists dependent relation between the two class object.
	 * 	In servlet,the message can represent servletEvent,the Subject can represent ServletContext,
	 * the Observer can represent ServletContextListener.
	 */
	private List<Observer> observers;
	/**
	 * Message of update
	 */
	private String message;
	/**
	 * If the status changed
	 */
	private boolean changed;
	/**
	 * Used to manage synchronization
	 */
	private final Object MUTEX= new Object();
	
	public MyTopic(){
		this.observers=new ArrayList<>();
	}
	
	@Override
	public void register(Observer obj) {
		if(obj == null) throw new NullPointerException("Null Observer");
		synchronized (MUTEX) {
			if(!observers.contains(obj)) observers.add(obj);
		}
	}

	@Override
	public void unregister(Observer obj) {
		synchronized (MUTEX) {
			observers.remove(obj);
		}
	}

	@Override
	public void notifyObservers() {
		List<Observer> observersLocal = null;
		//synchronization is used to make sure any observer registered after message is received is not notified
			if (!changed)
				return;
			observersLocal = new ArrayList<>(this.observers);
			this.changed=false;
		for (Observer obj : observersLocal) {
			/**
			 * There can generate a event which contain itSelf.For servlet it is servletEvent.
			 */
			obj.update();
		}

	}

	@Override
	public Object getUpdate() {
		return this.message;
	}
	
	//method to post message to the topic
	public void postMessage(String msg){
		System.out.println("Message Posted to Topic:"+msg);
		this.message=msg;
		this.changed=true;
		notifyObservers();
	}

}
