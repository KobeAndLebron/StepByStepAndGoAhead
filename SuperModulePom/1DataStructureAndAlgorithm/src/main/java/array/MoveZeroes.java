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

    /**
     * 最坏时间复杂度为 O(n ^ 2).
     * nums全为0的时候为最坏情况.
     * @param nums
     */
    public void moveZeroes2(int[] nums) {
        if (nums != null) {
            for (int i = 0, j = 0; j < nums.length; i++,j++) {
                if (nums[i] == 0) {
                    j = indexOfNonZero(nums, i + 1);
                    if (j < nums.length) {
                        nums[i] = nums[j];
                        nums[j] = 0;
                    }
                }
            }
        }
    }

    private int indexOfNonZero(int nums[], int startIndex) {
        int i = startIndex;
        for(; i < nums.length; i++) {
            if (nums[i] != 0) {
                return i;
            }
        }
        return i;
    }


}
