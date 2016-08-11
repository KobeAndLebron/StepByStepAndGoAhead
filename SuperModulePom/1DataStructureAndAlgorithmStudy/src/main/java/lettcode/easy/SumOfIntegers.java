package lettcode.easy;

import java.util.LinkedList;
import java.util.List;

/**
 * Calculate the sum of two integers, but you are not allowed to use operator + and -.
 * 
 * @author ChenJingShuai 1 Aug 2016
 *
 */
public class SumOfIntegers {
	public int getSum(int a, int b){
		int returnI = 0;
		// binary array of a
		List<Integer> bitA = getBinaryArray(a);
		// binary array of b
		List<Integer> bitB = getBinaryArray(b);
		
		int bitCLength = (bitA.size() > bitB.size() ? bitA.size() : bitB.size()) + 1;
		// final array of binary
		int[] bitC = new int[bitCLength];
		int num = 0;
		int i = 0;
		int j = 0;
		boolean isFlag = false;
		// add binary array
		for(;i < bitA.size() || j < bitB.size();i++,j++){
			int bit1 = i < bitA.size() ? bitA.get(i) : 0;
			int bit2 = j < bitB.size() ? bitB.get(j) : 0;
			if((bit1 ^ bit2) == 0){
				if(bit1 == 0){// 都是0
					if(isFlag){
						bitC[bitCLength - 1 - num++] = 1;
					}else{
						bitC[bitCLength - 1 - num++] = 0;
					}
					isFlag = false;
				}else{ // 都是1
					if(isFlag){
						bitC[bitCLength - 1 - num++] = 1;
					}else{
						bitC[bitCLength - 1 - num++] = 0;
					}
					isFlag = true;
				}
			}else{
				if(isFlag){
					bitC[bitCLength - 1 - num++] = 0;
					isFlag = true;
				}else{
					bitC[bitCLength - 1 - num++] = 1;
					isFlag = false;
				}
			}
		}
		
		// 最后一次进位不用管,因为进位的数字肯定是0,0*2^n = 0
		
		for(int k = 0; k < bitCLength; k++){
			returnI += bitC[k] * Math.pow(2, bitCLength - k - 1);
		}
		return returnI;
	}
	
	public List<Integer> getBinaryArray(int i){
		List<Integer> list = new LinkedList<>();
		while(i >= 0){
			list.add(i % 2);
			if(i == 0) break;
			i = i / 2;
		}
		return list;
	}
}
