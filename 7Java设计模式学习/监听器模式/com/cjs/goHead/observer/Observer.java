package com.cjs.goHead.observer;

/**
 * 
 * 	Observer contains a method to set the subject to watch and another method will be used by subject to notify
 * them of any changes of subject.
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年7月5日-下午9:47:14
 */
public interface Observer {
	
	//method to update the observer, used by subject
	public void update();
	
	//attach with subject to observe
	public void setSubject(Subject sub);
}
