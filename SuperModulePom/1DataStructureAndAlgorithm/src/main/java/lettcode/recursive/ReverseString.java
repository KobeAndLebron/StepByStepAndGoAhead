package lettcode.recursive;

/**
 * Created by chenjingshuai on 19-3-11.
 */
public class ReverseString {
    // 解法一
    public static String reverse(String str) {
        return reverseRecursive(0, str);
    }

    /**
     * 递归:
     * 将翻转index...length的函数定义为:
     *   1.先翻转index+1...length的字符串.
     *   2.然后将第index的字符追加到1的结果, 最后返回.
     * 递归结束条件, 当只剩一个字符的时候, 直接返回.
     *
     * @param index
     * @param str
     * @return
     */
    private static String reverseRecursive(int index, String str) {
        String returnStr = Character.toString(str.charAt(index));
        if (index < str.length() - 1) { // 只要还有两个字符, 就递归调用.
            returnStr = reverseRecursive(index + 1, str) + returnStr;
        }
        return returnStr;
    }

    // 解法二, 空间复杂度O(1).
    public static void reverseString(char[] s) {
        helper(0, s.length - 1, s);
    }

    private static void helper(int start, int end, char[] s) {
        if (start >= end) {
            return;
        }

        // swap between the first and the last elements.
        char tmp = s[start];
        s[start] = s[end];
        s[end] = tmp;

        helper(start + 1, end - 1, s);
    }

}
