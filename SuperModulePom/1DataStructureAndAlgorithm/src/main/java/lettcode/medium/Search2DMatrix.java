package lettcode.medium;

/**
 * 搜索矩阵相关题目.
 */
public class Search2DMatrix {
    /**
     *
     * 第一题: https://leetcode.com/problems/search-a-2d-matrix
     *  矩阵特征: 1. 每一行从左到右递增 2. 当前行的第一个数比上一行的最后一个数大.
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }
        int length = matrix.length * matrix[0].length;

        // 全局递增, 变相的二分搜索: 将矩阵当成一个全局递增的一维数组.
        int start = 0;
        int end = length - 1;
        while (start <= end) {
            int middle = start + ((end - start) >> 1);
            int x = middle / matrix[0].length; // 由全局长度得到的x.
            int y = middle % matrix[0].length; // 由全局长度得到的y.
            if (matrix[x][y] > target) {
                end = middle - 1;
            } else if (matrix[x][y] < target) {
                start = middle + 1;
            } else {
                return true;
            }
        }

        return false;
    }

    /**
     * 第二题: https://leetcode.com/problems/search-a-2d-matrix-ii
     *  矩阵满足: 1. 每一行从左往右递增 2. 每一列从上往下递增.
     *
     *  要求时间复杂度为 O(M+N), M和N分别为矩阵的长和宽. 空间复杂度为O(1).
     *
     * @param matrix
     * @param target
     * @return
     */
    public boolean searchMatrix2_1(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }
        // 抖机灵的题目: 从右上角或左下角开始找, 这样每次都能排除一行或者一列.
        // 这里采取从右上角找的方式. 从左下角找查看方法 searchMatrix2_2.
        int startX = 0;
        int startY = matrix[0].length - 1;

        while (startX <= matrix.length - 1 && startY >= 0) {
            // 目标值大于右上角的值, 说明这一行的值都不符合要求, 列不变, 行往下移.
            if (matrix[startX][startY] < target) {
                startX++;
            } else if (matrix[startX][startY] > target) {
                // 目标值小于左上角的值, 说明这一列的值都不符合要求, 行不变, 列往前移.
                startY--;
            } else {
                return true;
            }
        }

        return false;
    }

    // 从左下角开始找.
    public boolean searchMatrix2_2(int[][] matrix, int target) {
        if (matrix.length == 0) {
            return false;
        }
        int startX = matrix.length - 1;
        int startY = 0;

        while (startY <= matrix[0].length - 1 && startX >= 0) {
            if (matrix[startX][startY] < target) {
                startY++;
            } else if (matrix[startX][startY] > target) {
                startX--;
            } else {
                return true;
            }
        }

        return false;
    }

    public static void main(String[] args) {
        int[][] matrix = new int[][]{
            new int[]{1, 3, 5, 7, 9},
            new int[]{10, 20, 30, 40, 60}
        };

        System.out.println(new Search2DMatrix().searchMatrix2_1(matrix, 0));
    }
}
