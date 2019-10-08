package sort;

import java.util.Arrays;

public class QuickSort {

    /**
     * ascending.
     * a b c d e f g
     * <p>
     * a b d e g f e
     *
     * @param nums
     * @return
     */
    public int[] sort(int[] nums) {
        quickSort(nums, 0, nums.length - 1);

        return nums;
    }

    public void quickSort(int[] nums, int start, int end) {
        if (start < end) {
            int pivotIndex = partition(nums, start, end);

            quickSort(nums, start, pivotIndex - 1);
            quickSort(nums, pivotIndex + 1, end);
        }

    }

    public int partition(int[] nums, int start, int end) {
        int pivot = nums[end];
        int pivotIndex = end;

//        end--;
        while (start < end) {
            while (nums[start] <= pivot && start < end) {
                start++;
            }

            while (nums[end] >= pivot && start < end) {
                end--;
            }

            swap(nums, start, end);
        }

        if (start == end) {
            if (nums[start] > pivot) {
                swap(nums, start, pivotIndex);
            }
        }
        return start;
    }

    public void swap(int[] nums, int left, int right) {
        int temp = nums[left] ^ nums[right];
        nums[left] = temp ^ nums[left];
        nums[right] = temp ^ nums[right];
    }

    public static void main(String[] args) {
        System.out.println(Arrays.toString(new QuickSort().sort(new int[]{-4, 0, 7, 4, 9, -5, -1, 0, -7, -1})));
    }
}
