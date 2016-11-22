package lettcode.easy;

import java.util.Arrays;

/**
 * Created by chenjingshuai on 16-11-22.
 */
public class RotateArray {
    public void rotate(int[] nums, int k) {
        if (nums != null && k <= nums.length - 1) {
            int middleIndex = nums.length - k;
            int[] tempArray = Arrays.copyOf(nums, middleIndex);
            System.arraycopy(nums, middleIndex, nums, 0, middleIndex);
            System.arraycopy(tempArray, 0, nums, k, middleIndex);
        }
    }
}
