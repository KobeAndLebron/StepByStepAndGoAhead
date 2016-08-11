package lettcode;

import java.util.HashMap;
import java.util.Map;

/**
 * 
 * 	Given an array of integers and and integer k,find out whether there are two distinct indices j
 * and i in the array such that nums[i] = num[j] and the distance between i and j is at most k.
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月13日-下午5:32:15
 */
public class ContainsDuplicate2HashMap {
    /*public boolean containsNearbyDuplicate(int[] nums, int k) {
    	if(nums == null || nums.length ==0 || k <= 0){
    		return false;
    	}
    	Map<Integer ,Object> hashMap = new HashMap<>();
    	for(int i = 0; i < nums.length; i++){
    		if(hashMap.containsKey(nums[i])){
    			return true;
    		}else{
    			hashMap.put(nums[i], null);
    		}
    		if(hashMap.size() == k + 1){
    			int removedIndex = i - k;
    			hashMap.remove(nums[removedIndex]);
    		}
    	}
    	return false;
    }*/
	public boolean containsNearbyDuplicate(int[] nums, int k) {
    	if(nums == null || nums.length ==0 || k <= 0){
    		return false;
    	}
    	Map<Integer ,Object> hashMap = new HashMap<>();
    	for(int i = 0; i < nums.length; i++){
    		/**
    		 * 在HashMap的实现方式里，会在放入Node-key和value组成-的同时返回以前的值。
    		 * 如果返回null，说明是第一次放值;否则是第二次即存在相同的值
    		 * 通过这个技巧可以避免contain，对事件效率有一定的提升
    		 * 同理利用set也是这个道理
    		 */
    		if(!(hashMap.put(nums[i], 1) == null)){
    			return true;
    		}
    		if(hashMap.size() == k + 1){
    			int removedIndex = i - k;
    			hashMap.remove(nums[removedIndex]);
    		}
    	}
    	return false;
    }
}
