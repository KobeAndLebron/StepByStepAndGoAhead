package lettcode;

/**
 * Counting Bits:
 * 	Given a non negative integer number num.For every numbers i in the range 0=<i<=num 
 * calculate the number of 1's in their binary representation and return them as an array
 * 
 * 暴力递归
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年3月31日-上午9:02:58
 */
public class CountingBits_Solution1 {
	public static int getBits(int num) {
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
			if (Math.pow(2, n) == num) { // 如果正好是最高位是1
				return 1;
			} else { //  减去最高位为1代表的数，然后算出剩下的数的二进制数即可---递归关键
				return getBits((int) (num - Math.pow(2, n - 1))) + 1;
			}
		} else if (num == 0) {
			return 0;
		} else {
			return 1;
		}
	}

	public static int[] countBits(int num) {
		int[] array = new int[num + 1];
		for(int i = 0; i <= num; i++){
			array[i] = getBits(i);
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
