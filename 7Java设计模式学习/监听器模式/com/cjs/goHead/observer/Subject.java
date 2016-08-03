package com.cjs.goHead.observer;

/**
 * 	Contain a list of Observers to notify of any changes in its state,so it should provide methods methods using 
 * which observers can register and unregister themselves.
 *  It also contains a method to notify all the observers of any change and either it can send the update(message)
 * while notifying the observer or it can can provide another method to get the update.
 * 
 * It is like servletContext and event.
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年7月5日-下午9:43:10
 */
public interface Subject {

	//methods to register and unregister observers
	public void register(Observer obj);
	public void unregister(Observer obj);
	
	//method to notify observers of change
	public void notifyObservers();
	
	//method to get updates from subject
	public Object getUpdate();
	
}
