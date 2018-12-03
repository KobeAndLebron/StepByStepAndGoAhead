package lettcode;

/**
 * Counting Bits:
 * 	Given a non negative integer number num.For every numbers i in the range 0=<i<=num 
 * calculate the number of 1's in their binary representation and return them as an array
 * 
 * 动态规划
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年3月31日-上午9:02:58
 */
public class CountingBits_Solution2 {
	public static int getBits(int num ,int[] array) {
		int numOf1 = 0;
		if(array[num] != 0){
			return array[num];
		}
		// 如果array[num]里面的值不为0，那么就证明此值已经被求过了
		if (num > 2) {
			int n = 0;
			/** 
			 * 算出最大的小于num的2的n次方的数
			 */
			for (n = 1;; n++) {
				if (Math.pow(2, n) >= num) {
					break;
				}
			}
			if (Math.pow(2, n) == num) {
				numOf1 = 1;
			} else {
				numOf1 = getBits((int) (num - Math.pow(2, n - 1)) ,array) + 1;
			}
		} else if (num == 0) {
			numOf1 = 0;
		} else {
			numOf1 = 1;
		}
		array[num] = numOf1;
		return numOf1;
	}

	public static int[] countBits(int num) {
		int[] array = new int[num + 1];
		int[] tempM = new int[num + 1];
		for(int i = 0; i <= num; i++){
			array[i] = getBits(i ,tempM);
		}
		return array;
	}

	public static void main(String[] args) {
		long s = System.currentTimeMillis();
		int[] nums = countBits(10000);
		long e = System.currentTimeMillis();
		System.out.println(nums + " , time : " + (e - s));
	}
}
