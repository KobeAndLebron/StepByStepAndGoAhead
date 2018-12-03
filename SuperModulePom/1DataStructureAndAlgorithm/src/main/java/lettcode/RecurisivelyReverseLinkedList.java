package lettcode;

/**
 * This solution is a recursive solution.
 *  Space complexity:O(n).The extra space comes from implicit stack space due to recursion.The recursion could 
 * go to n levels deep.
 * 	Time complexity:O(n)
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月12日-下午8:26:45
 */
public class RecurisivelyReverseLinkedList {
	/**
	 * 每一次递归所返回的节点都是reverse之后的头结点
	 * 
	 * @param head
	 * @return
	 */
	public ListNode reverseList(ListNode head) {
		if (head == null || head.next == null){
			return head;
		}
		ListNode p = reverseList(head.next);
		head.next.next = head;
		head.next = null;
		return p;
	}
}
