package com.cjs.HashMapAndHashtable;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HashMap和Hashtable的快速失败策略 {
	
	public static void main(String[] args) {
		Map<String , String> hashMap = new HashMap<String , String>();
		
		hashMap.put("aa", "kobe");
		hashMap.put("bb1", "lebron");
		
		Target1 target1 = new Target1(hashMap);
		Target2 target2 = new Target2(hashMap);
		
		
		Thread thread1 = new Thread(target1);
		Thread thread2 = new Thread(target2);
		
		thread1.start();
		thread2.start();
	
	}
	
}

class Target1 implements Runnable{
	private Map<String , String> map;
		
	public Target1(Map<String , String> map){
		this.map = map;
	}
	
	@Override
	public void run() {
		Iterator<Map.Entry<String, String>> iterator = map.entrySet().iterator();
		
		for(;iterator.hasNext();){
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			map.put("bb", "lebron");
			Map.Entry<String, String> ele = iterator.next();
			System.out.println(ele.getKey() + " : " + ele.getValue());
		}
	}
	
}

class Target2 implements Runnable{
	private Map<String , String> map;
	
	public Target2(Map<String , String> map){
		this.map = map;
		System.out.println(map.size());
	}
	
	@Override
	public void run() {
//		try {
//			Thread.sleep(10);
//		} catch (InterruptedException e) {
//			e.printStackTrace();
//		}
//		map.put("bb", "lebron");
	}
	
}
