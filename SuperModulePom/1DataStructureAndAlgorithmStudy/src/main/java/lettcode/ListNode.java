package lettcode;

/**
 * Created by chenjingshuai on 16-11-30.
 */
public class ListNode {
    public int val;
    public ListNode next;
    public ListNode() { }
    public ListNode(int x) { val = x; }

    private ListNode(int... array) {
        if (array != null) {
            ListNode listNode = this;
            for (int i = 0; i < array.length; i++) {
                listNode.val = array[i];
                if (i != array.length - 1) {
                    listNode.next = new ListNode();
                    listNode = listNode.next;
                }
            }
        }
    }

    public static ListNode generateListNode(int... array) {
        ListNode listNode = null;
        if (array != null && array.length != 0) {
            listNode = new ListNode(array);
        }
        return listNode;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        ListNode listNode = this;
        while (listNode != null) {
            ListNode temp = listNode.next;
            sb.append(listNode.val);
            if (temp != null) {
                sb.append("->");
            }
            listNode = temp;
        }
        return sb.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj != null && obj instanceof ListNode) {
            ListNode listNode1 = (ListNode) obj;
            ListNode listNode2 = this;
            while (listNode1 != null && listNode2 != null) {
                if (listNode1.val != listNode2.val) {
                    return false;
                }
                listNode1 = listNode1.next;
                listNode2 = listNode2.next;
            }
            return listNode1 == null && listNode2 == null;
        } else {
            return false;
        }
    }
}
