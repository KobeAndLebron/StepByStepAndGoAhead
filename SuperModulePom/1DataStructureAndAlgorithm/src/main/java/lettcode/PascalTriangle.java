package lettcode;

import java.util.ArrayList;
import java.util.List;

/**
 *  时间原因贴出在线判断的网址:
 *     https://leetcode.com/explore/learn/card/recursion-i/251/scenario-i-recurrence-relation/1659/
 *     https://leetcode.com/explore/learn/card/recursion-i/251/scenario-i-recurrence-relation/1660/
 *
 * Created by chenjingshuai on 19-3-11.
 */
public class PascalTriangle {
    /**
     * Given a non-negative integer numRows, generate the first numRows of Pascal's triangle.
     In Pascal's triangle, each number is the sum of the two numbers directly above it.

     Example:

     Input: 5
     Output:
     [
         [1],
        [1,1],
       [1,2,1],
      [1,3,3,1],
     [1,4,6,4,1]
     ]
     * @param numRows
     * @return
     */
    public List<List<Integer>> generate(int numRows) {
        if (numRows < 0) {
            return null;
        } else {
            List<List<Integer>> triangle = new ArrayList<>(numRows);
            for (int i = 0; i < numRows; i++) {
                int ithRowSize = i + 1;
                List<Integer> iThRow = new ArrayList<>(ithRowSize);
                for (int j = 0; j < ithRowSize; j++) {
                    if (j == 0 || j == ithRowSize - 1) { // 第一个和倒数第一个置为1.
                        iThRow.add(1);
                    } else { // 其余位置的值置为上一行第j-1和第j个值的和.
                        List<Integer> preRow = triangle.get(i - 1);
                        iThRow.add(preRow.get(j) + preRow.get(j - 1));
                    }
                }
                triangle.add(iThRow);
            }
            return triangle;
        }
    }


    /**
     * Given a non-negative index k where k ≤ 33, return the kth index row of the Pascal's triangle.

     Note that the row index starts from 0.


     In Pascal's triangle, each number is the sum of the two numbers directly above it.

     Example:

     Input: 3
     Output: [1,3,3,1]
     Follow up:

     Could you optimize your algorithm to use only O(k) extra space?
     * @param rowIndex
     * @return
     */
    public List<Integer> getRow(int rowIndex) {
        if (rowIndex < 0) {
            return null;
        }
        int size = rowIndex + 1;
        List<Integer> iThRow = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            // 用于暂存前一个index的值.
            int preValue = 1;
            iThRow.add(1); // 每次循环扩大一次列表的大小.
            for (int j = 1; j <= i - 2; j++) {
                int temp = iThRow.get(j);
                iThRow.set(j, iThRow.get(j) + preValue);
                preValue = temp;
            }
        }
        return iThRow;
    }

    /**
     * 从后往前算, 不需要一个变量来暂存.
     * @param rowIndex
     * @return
     */
    public List<Integer> getRow1(int rowIndex) {
        if (rowIndex < 0) {
            return null;
        }
        int size = rowIndex + 1;
        List<Integer> iThRow = new ArrayList<>(size);
        for (int i = 1; i <= size; i++) {
            iThRow.add(1); // 每次循环扩大一次列表的大小.
            for (int j = i - 2; j >= 1; j--) {
                iThRow.set(j, iThRow.get(j) + iThRow.get(j - 1));
            }
        }
        return iThRow;
    }
}
