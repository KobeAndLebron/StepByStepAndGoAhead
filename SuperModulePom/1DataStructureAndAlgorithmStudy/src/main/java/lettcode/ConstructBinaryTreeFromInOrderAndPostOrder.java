package lettcode;

/**
 * Construct binary tree from Inorder and Postorder traversal
 * 
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月3日-下午5:18:29
 */
/**
 * Definition for a binary tree node.
 * public class TreeNode {
 *     int val;
 *     TreeNode left;
 *     TreeNode right;
 *     TreeNode(int x) { val = x; }
 * }
 */
public class ConstructBinaryTreeFromInOrderAndPostOrder {
	public TreeNode buildTree(int[] inorder, int[] postorder) {
		if(inorder ==null || postorder == null){
			return null;
		}
		if(postorder.length == 0 || inorder.length ==0){
			return null;
		}
        return recursivelyBuild(inorder, postorder, 0, inorder.length - 1 , inorder.length - 1);
    }
	
	private TreeNode recursivelyBuild(int[] inorder ,int[] postorder ,int start ,int end , int rootIndex){
		if(start > end || start < 0 || start > postorder.length - 1 || end < 0 || end > postorder.length - 1){
			return null;
		}else if(start < end){
			int	rootVal = postorder[rootIndex];
			int rootIndexInInorder = getIndexInInorder(inorder, rootVal);
			TreeNode rootNode = new TreeNode(rootVal);
			
			// 右子树节点的个数
			int rightTreeNodeNums = end - rootIndexInInorder;
			
			// 如果没有左子树
			if(rightTreeNodeNums == end - start){
				int rightRootIndex = rootIndex - 1;
				rootNode.right = recursivelyBuild(inorder, postorder, rootIndexInInorder + 1, end , rightRootIndex);
				rootNode.left = null;
			}else {
				int leftRootIndex = rootIndex - rightTreeNodeNums - 1;
				int rightRootIndex = rootIndex - 1;
				rootNode.right = recursivelyBuild(inorder, postorder, rootIndexInInorder + 1, end , rightRootIndex);
				rootNode.left = recursivelyBuild(inorder, postorder, start, rootIndexInInorder - 1 , leftRootIndex);
			}
			
			return rootNode;
		}else{
			return new TreeNode(inorder[start]);
		}
	}
	
	/**
	 * 根据值找到其在前序数组的索引
	 * 
	 * @param inorder
	 * @param val
	 * @return
	 */
	private int getIndexInInorder(int [] inorder ,int val){
		for(int i = 0; i < inorder.length; i++){
			if(val == inorder[i]){
				return i;
			}
		}
		return -1;
	}
	public static void main(String[] args) {
		int[] a = new int[]{3,2,1};
		int[] b = new int[]{3,2,1};
		new ConstructBinaryTreeFromInOrderAndPostOrder().buildTree(a, b);
	}
}

class TreeNode {
    int val;
    TreeNode left;
    TreeNode right;
    TreeNode(int x) { val = x; }
}