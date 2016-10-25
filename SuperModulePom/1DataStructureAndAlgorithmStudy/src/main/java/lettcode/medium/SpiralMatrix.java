package lettcode.medium;

import java.util.Collections;
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
        if (matrix == null) {
            return null;
        }
        if (matrix.length == 0 || matrix[0].length == 0) {
            return Collections.EMPTY_LIST;
        }
        return getList(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }

    /**
	 * 分别从上/下 左/右取数据然后组装成spiral,每一次递归产生一个螺旋
	 * @param matrix 矩阵
	 * @return 螺旋状数组
	 */
	private List<Integer> getList(int[][] matrix, int up, int down, int left, int right){
		List<Integer> outPutList = new LinkedList<>();

		if (down >= up && right >= left) {
			// traverse up side.
			for(int i = left; i <= right; i++) {
				outPutList.add(matrix[up][i]);
			}
			up++;
			// traverse right side.
			for(int i = up; i <= down; i++) {
				outPutList.add(matrix[i][right]);
			}
			right--;

			if (up <= down) {
				// travel down side.
				for(int i = right; i >= left; i--) {
					outPutList.add(matrix[down][i]);
				}
				down--;
				// travel left side.
				if (right >= left) {
					for(int i = down; i >= up; i--) {
						outPutList.add(matrix[i][left]);
					}
					left++;
				}
			}
		}
		if (down >= up && right >= left) {
			outPutList.addAll(getList(matrix, up, down, left, right));
		}

		return outPutList;
	}
}
