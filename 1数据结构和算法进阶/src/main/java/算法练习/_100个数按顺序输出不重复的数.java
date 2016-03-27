package 算法练习;

import java.util.HashSet;
import java.util.Iterator;

public class _100个数按顺序输出不重复的数 {
	public static void main(String[] args) {
		int[] array = getRandomIntArray(100);
		HashSet<Integer> hashSet = new HashSet<>();
		for(int ele : array){
			hashSet.add(ele);
		}
		Iterator<Integer> iterator = hashSet.iterator();
		int temp = Integer.MIN_VALUE;
		for(;iterator.hasNext();){
			Integer ele = iterator.next();
			if(temp < ele){
				System.out.println(ele);
				temp = ele;
			}else{
				System.out.println(ele);
				System.out.println("结果错误");
			}
		}
	}
	
	public static int[] getRandomIntArray(int size){
		int[] array = new int[size];
		for(int i = 0; i < size; i++){
			array[i] = (int)(Math.random() * 100) + 1;
		}
		return array;
	}
}
