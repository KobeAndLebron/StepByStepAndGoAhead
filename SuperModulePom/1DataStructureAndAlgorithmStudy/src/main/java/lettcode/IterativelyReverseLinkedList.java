package lettcode;

/**
 * Reverse a singly linked list.And this solution is a iterative solution.
 * 
 * Definition for singly-linked list.
 * public class ListNode {
 *     int val;
 *     ListNode next;
 *     ListNode(int x) { val = x; }
 * }
 */
public class IterativelyReverseLinkedList {
	/**
	 * 将null也作为一个特殊的节点,可以避免代码的冗长
	 * @param head
	 * @return
	 */
	public ListNode reverseList(ListNode head) {
        ListNode pre = null;
        ListNode post = head;
        while(post != null){
        	ListNode temp = post.next;
        	post.next = pre;
        	pre = post;
        	post = temp;
        }
        return pre;
    }
	
	public ListNode reverseList1(ListNode head) {
        if(head == null){
        	return null;
        }
        ListNode pre = head;
        ListNode post = pre.next;
        while(post != null){
        	ListNode temp = post.next;
        	post.next = pre;
        	pre = post;
        	post = temp;
        }
        head.next = null;
        return pre;
    }
}

class ListNode {
	int val;
	ListNode next;
	ListNode(int x) { val = x; }
}