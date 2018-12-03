package com.cjs.String;

public class StringPractice {
	public static void main(String[] args) {
		System.out.println(replace("aaa", 'c', 'c'));
	}
	
	public static String replace(String str , char oldChar, char newChar) {
		if(oldChar != newChar && str != null){
			int len = str.length();
			// 是否出现了oldChar,可以避免创建重复的字符串在oldChar没出现在str中的时候。String 的原则：避免创建重复内容(euqals)的字符串
			boolean flag = false;
			char[] strToChar = str.toCharArray();
			int i = 0;
			while(i < len){
				if(strToChar[i] == oldChar){
					strToChar[i] = newChar;
					flag = true;
				}
				i++;
			}
			if(flag){
				return new String(strToChar);
			}
		}
		return str;
	}
}
