package lettcode;

/**
 * 
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
			if (Math.pow(2, n) == num) {
				return 1;
			} else {
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
		int[] nums = countBits(5);
		System.out.println(nums);
	}
}
