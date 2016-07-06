package com.cjs.goHead.监听器模式;

public class MyObserver implements Observer{
	private String name;
	private Subject topic;
	
	public MyObserver(String nm){
		this.name=nm;
	}
	@Override
	public void update(// ServletEvent servletEvent
			) {
		// get the message of subject update
		String msg = (String) topic.getUpdate();
		if(msg == null){
			System.out.println(name+":: No new message");
		}else
		System.out.println(name+":: Consuming message::"+msg);
	}

	@Override
	public void setSubject(Subject sub) {
		this.topic=sub;
	}

}
