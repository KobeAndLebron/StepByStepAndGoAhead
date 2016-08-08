package com.gohead.shared.test;

public class Data {
	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public boolean equals(Object obj){
		if(obj == null){
			return false;
		}else{
			if(obj instanceof Data){
				Data data = (Data) obj;
				return data.name.equals(this.name);
			}else{
				return false;
			}
		}
	}
}
