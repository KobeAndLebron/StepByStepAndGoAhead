package array;

/**
 *
 * https://leetcode.com/problems/move-zeroes/
 *
     Given an array nums, write a function to move all 0's to the end of it while maintaining the relative
 order of the non-zero elements.

     Example:

     Input: [0,1,0,3,12]
     Output: [1,3,12,0,0]
     Note:

     You must do this in-place without making a copy of the array.
     Minimize the total number of operations.

 * @author chenjingshuai
 * @date 19-4-25
 */
public class MoveZeroes {
    public void moveZeroes(int[] nums) {
        // 0的数量
        int zeroCount = 0;
        // 当前需要赋值的位置.
        int position = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (position != i) {
                    nums[position] = nums[i];
                }
                position++;
            } else {
                zeroCount++;
            }
        }

        for (int i = 1; i <= zeroCount; i++) {
            nums[nums.length - i] = 0;
        }
    }

    public void moveZeroes1(int[] nums) {
        // 当前需要赋值的位置.
        int position = 0;

        for (int i = 0; i < nums.length; i++) {
            if (nums[i] != 0) {
                if (position != i) {
                    nums[position] = nums[i];
                }
                position++;
            }
        }

        for (; position < nums.length; position++) {
            nums[position] = 0;
        }
    }


}
