package lettcode.medium;

import java.util.ArrayList;
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
		if(matrix.length == 0){
			return null;
		}
        return matrix == null ? null : getList(matrix, 0, matrix.length - 1, 0, matrix[0].length - 1);
    }
	
	/**
	 * 分别从上/下 左/右取数据然后组装成spiral,每一次递归产生一个螺旋
	 * @param matrix 矩阵
	 * @return 螺旋状数组
	 */
	private List<Integer> getList(int[][] matrix, int up, int down, int left, int right){
		List<Integer> outPutList = new ArrayList<>();
		
		if(matrix != null && up <= matrix.length - 1 && down >=0 && right >= 0 && left <= matrix[0].length - 1
				&& up >=0 && down <= matrix.length - 1 && right <= matrix[0].length - 1 && 
				left >= 0){
			outPutList = getList(matrix, up + 1, down - 1, left + 1, right - 1);
			List<Integer> upList = new ArrayList<>();
			List<Integer> downList = new ArrayList<>();
			List<Integer> leftList = new ArrayList<>();
			List<Integer> rightList = new ArrayList<>();
			if(up <= down){
				for(int i = left; i <= right - 1; i++ ){ // 上
					upList.add(matrix[up][i]);
				}
				if(up != down){
					upList.remove(upList.size() - 1);
					for(int i = right; i >= left; i-- ){ // 下
						downList.add(matrix[down][i]);
					}
				}
				if(downList.size() > 0){
					downList.remove(downList.size() - 1);
				}
			}
			if(left <= right){
				if(downList.size() > 0){
					downList.remove(downList.size() - 1);
				}
				for(int i = down; i >= up; i-- ){ // 左
					leftList.add(matrix[i][left]);
				}
				if(left != right){
					
					for(int i = up; i <= down; i++ ){ // 右
						rightList.add(matrix[i][right]);
					}
				}
				if(rightList.size() > 0){
					rightList.remove(rightList.size() - 1);
				}
				if(leftList.size() > 0){
					leftList.remove(leftList.size() - 1);
				}
			}
			outPutList.addAll(upList);
			outPutList.addAll(rightList);
			outPutList.addAll(downList);
			outPutList.addAll(leftList);
		}
		
		return outPutList;
	}
	
	public static void main(String[] args) {
		System.out.println(new SpiralMatrix().spiralOrder(new int[][]{new int[]{2,3}}));
	}
}
