package lettcode;

import static java.lang.Math.*;
/**
 * 	Given an array of numbers nums, in which exactly two elements appear only once and all the other elements appear exactly twice. 
 * Find the two elements that appear only once. 
 *  For example:
 *
 *	Given nums = [1, 2, 1, 3, 2, 5], return [3, 5].
 *
 *	Note:
 *
 *	    The order of the result is not important. So in the above example, [5, 3] is also correct.
 *	    Your algorithm should run in linear runtime complexity. Could you implement it using only constant space complexity?
 *
 * 
 * 	异或：位相同为0，不相同为1
 * 	两个相同的数异或为0，一个数异或0还为其本身
 *
 * 	关键：
 * 		1 异或所有数找得到不同的那俩数的异或
 * 		2 找出1所得到的结果中位数为1的位（目前用了3种方法）
 * 		3 利用2的结果将数组分为differentNumber1 others 和 differentNumber2 others
 * 		4 分别将differentNumber1 others 和 differentNumber2 others进行异或，得到不同的两个数
 * 
 * 	
 * @author ChenJingShuai 21 Jun 2016
 *
 */
public class SingleNumber {
	public int[] singleNumber(int[] nums) {
        int[] twoDiffNumbers = new int[2];
        
        if(nums == null || nums.length < 2){
        	return null;
        }
        
        int result = 0;
        for(int i : nums){
        	result ^= i;
        }
        
        int temp = getThePositionOfLast1(result);
        // int temp = getThePositionOfFirst1(result);
        // int temp = getThePositionOfLast1_2(result);
        
        int first = 0;
        int second = 0;
        for(int i : nums){
        	if((i & temp) == 0){
        		first ^= i;
        	}else{
        		second ^= i;
        	}
        }
        twoDiffNumbers[0] = first;
        twoDiffNumbers[1] = second;
        return twoDiffNumbers;
    }
	
	/**
	 * 常规方法从右边找出1
	 * 
	 * @see SingleNumberTest#test2()
	 * @param nums
	 * @return
	 */
	public int getThePositionOfLast1(int number){
		/**
		 * 使用2是错误的，可能会造成死循环，只能使用最小值1 @see SingleNumberTest#test2()
		 */
		// int temp = 2;
        
		int temp = 1;
        // 找出从右边的第一个1
        while((temp & number) == 0){
        	temp <<= 1;
        }
        return temp;
	}
	
	/**
	 * 常规方法从左边找出1
	 * @param nums
	 * @return
	 */
	public int getThePositionOfFirst1(int number){
		/** 
		 *  int占4个字节，1个字节8位，一共32位，又有一位代表符号位，所以仅含1个1的最大的数为2的30次方
			如果超出int的限制(eg: 2^31)，那么按照溢出处理，即(2 ^ 31 - 1)处理
			
			只能使用最大值，理由同上个方法
		**/
		int temp = (int) pow(2, 30);
        
        // 找出从左边的第一个1
        while((temp & number) == 0){
        	temp >>= 1;
        }
        return temp;
	}
	
	/**
	 * 快速方法从右边找出1
	 * @param nums
	 * @return
	 */
	public int getThePositionOfLast1_2(int number){
        return number &= -number;
	}
}
