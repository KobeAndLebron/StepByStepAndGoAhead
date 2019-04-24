package array;

import java.util.Arrays;

/**
 *
 * @author chenjingshuai
 * @date 19-4-24
 */
public class RemoveDuplicatedItemsFromSortedArray {
    public int removeDuplicates(int[] nums) {
        int position = 0;
        int length = nums.length;

        for (int i = 0; i < nums.length - 1; ) {
            if (nums[i] != nums[i + 1]) {
                if (position != i) {
                    nums[++position] = nums[i + 1];
                } else {
                    position++;
                }
                i++;
            } else {
                for (int j = i; j < nums.length - 1; j++) {
                    i = j + 1;
                    if (nums[j] != nums[j + 1]) {
                        nums[++position] = nums[j + 1];
                        break;
                    } else {
                        length--;
                    }
                }
            }
        }

        return length;
    }

    public static void main(String[] args) {
        int[] nums = new int[]{1, 1};
        System.out.println(new RemoveDuplicatedItemsFromSortedArray().removeDuplicates(nums));
        System.out.println(Arrays.toString(nums));
    }
}
