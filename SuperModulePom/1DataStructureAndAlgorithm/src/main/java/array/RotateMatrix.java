package array;

import java.util.Arrays;

/**
 *
 *
 *   https://leetcode.com/explore/interview/card/top-interview-questions-easy/92/array/770

     You are given an n x n 2D matrix representing an image.
     Rotate the image by 90 degrees (clockwise).

     Note:
     You have to rotate the image in-place, which means you have to modify the input 2D matrix directly. DO NOT
 allocate another 2D matrix and do the rotation.

     Example 1:
     Given input matrix =
     [
         [1,2,3],
         [4,5,6],
         [7,8,9]
     ],

     rotate the input matrix in-place such that it becomes:
     [
         [7,4,1],
         [8,5,2],
         [9,6,3]
     ]

     Example 2:
     Given input matrix =
     [
         [ 5, 1, 9,11],
         [ 2, 4, 8,10],
         [13, 3, 6, 7],
         [15,14,12,16]
     ],

     rotate the input matrix in-place such that it becomes:
     [
         [15,13, 2, 5],
         [14, 3, 4, 1],
         [12, 6, 8, 9],
         [16, 7,10,11]
     ]
 *
 * @author chenjingshuai
 * @date 19-5-10
 */
public class RotateMatrix {

    /**
     * 自己实现, 对应解释:　http://javabypatel.blogspot.com/2016/11/rotate-matrix-by-90-degrees-inplace.html
     * 
     * @param matrix
     */
    public void rotate(int[][] matrix) {
        for (int i = 0; i < matrix.length / 2; i++) {
            for (int j = i; j < matrix.length - 1 - i; j++) {
                // 上.
                int up = matrix[i][j];
                // 右.
                int right = matrix[j][matrix.length - i - 1];
                // 下.
                int down = matrix[matrix.length - i - 1][matrix.length - j - 1];
                // 左.
                int left = matrix[matrix.length - j - 1][i];

                // 上 -> 右.
                matrix[j][matrix.length - i - 1] = up;
                // 右 -> 下.
                matrix[matrix.length - i - 1][matrix.length - j - 1] = right;
                // 下 -> 左.
                matrix[matrix.length - j - 1][i] = down;
                // 左 -> 上.
                matrix[i][j] = left;
            }
        }
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]
            {
                {5, 1, 9, 11},
                {2, 4, 8, 10},
                {13, 3, 6, 7},
                {15, 14, 12, 16}
            };
        int[][] matrix1 = new int[][]
            {
                {5, 1, 9},
                {2, 4, 8},
                {13, 3, 6},
            };
        test(matrix);
        test(matrix1);
    }

    private static void test(int[][] matrix) {
        new RotateMatrix().rotate(matrix);
        for (int[] row : matrix) {
            System.out.println(Arrays.toString(row));
        }

        System.out.println();
    }
}
