package lettcode.easy;

/**
 * Created by chenjingshuai on 16-11-4.
 */
public class MoveZeros {
    public void moveZeroes(int[] nums) {
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

