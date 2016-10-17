package lettcode.medium;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

/**
 * Given a matrix of m x n elements (m rows, n columns), return all elements of the matrix in spiral order.

	For example,
	Given the following matrix:
	
	[
	 [ 1, 2, 3 ],
	 [ 4, 5, 6 ],
	 [ 7, 8, 9 ]
	]

	You should return [1,2,3,6,9,8,7,4,5]. 
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年7月10日-下午4:30:49
 *
 */
public class SpiralMatrix {
	public List<Integer> spiralOrder(int[][] matrix) {
		if (matrix == null || matrix.length == 0 || matrix[0].length == 0) {
			return null;
		}
		return getList(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }
	
	/**
	 * 分别从上/下 左/右取数据然后组装成spiral,每一次递归产生一个螺旋
	 * @param matrix 矩阵
	 * @return 螺旋状数组
	 */
	private List<Integer> getList(int[][] matrix, int up, int down, int left, int right){
		List<Integer> outPutList = new ArrayList<>();

		if (down > up || right >= left) {
			List<Integer> upList = new LinkedList<>();
			List<Integer> downList = new LinkedList<>();
			if (left <= right) { // return left and right list.
				for(int i = left; i <= right; i++) {
					upList.add(matrix[up][i]);
				}
				for(int i = right; i <= left; i--) {
					downList.add(matrix[down][i]);
				}
			}

			int temp = up + 1;
			List<Integer> leftList = new LinkedList<>();
			List<Integer> rightList = new LinkedList<>();
			if (up < down) { // return up and down list.
				down--;
				for(int i = up; i <= down; i++) {
					leftList.add(matrix[i][right]);
				}
				for(int i = down; i <= up; i--) {
					rightList.add(matrix[i][left]);
				}

			}

			outPutList = getList(matrix, up + 1, down - 1, left + 1, right - 1);
			outPutList.addAll(upList);
			outPutList.addAll(rightList);
			outPutList.addAll(downList);
			outPutList.addAll(leftList);
		}

		// The List which contains up right down is a spiral.
		return outPutList;
	}
	
	public static void main(String[] args) {
		System.out.println(new SpiralMatrix().spiralOrder(new int[][]{new int[]{2,3}}));
	}
}
