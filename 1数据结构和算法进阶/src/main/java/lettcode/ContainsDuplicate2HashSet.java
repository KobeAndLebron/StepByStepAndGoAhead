package lettcode;

import java.util.HashSet;
import java.util.Set;

/**
 *  Given an array of integers and and integer k,find out whether there are two distinct indices j
 * and i in the array such that nums[i] = num[j] and the distance between i and j is at most k.
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月13日-下午5:34:42
 */
public class ContainsDuplicate2HashSet {
    /*public boolean containsNearbyDuplicate(int[] nums, int k) {
    	if(nums == null || nums.length ==0 || k <= 0){
    		return false;
    	}
    	Set<Integer> hashSet = new HashSet<>();
    	for(int i = 0; i < nums.length; i++){
    		if(hashSet.contains(nums[i])){
    			return true;
    		}else{
    			hashSet.add(nums[i]);
    		}
    		if(hashSet.size() == k + 1){
    			int removedIndex = i - k;
    			hashSet.remove(nums[removedIndex]);
    		}
    	}
    	return false;
    }*/
	/**
	 * this solution is the most fastest.
	 * 
	 * 利用了Set的add方法特性：添加成功返回true，添加失败返回false.
	 * 将两次contain变为一次contain
	 * 
	 * @param nums
	 * @param k
	 * @return
	 */
    public boolean containsNearbyDuplicate(int[] nums, int k) {
    	if(nums == null || nums.length ==0 || k <= 0){
    		return false;
    	}
    	Set<Integer> hashSet = new HashSet<>();
    	for(int i = 0; i < nums.length; i++){
    		if(!hashSet.add(nums[i])){
    			return false;
    		}
    		if(hashSet.size() == k + 1){
    			int removedIndex = i - k;
    			hashSet.remove(nums[removedIndex]);
    		}
    	}
    	return false;
    }
}
