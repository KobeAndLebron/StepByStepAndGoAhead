package lettcode;

/**
 * 
 * Given an integer, write a function to determine if it is a power of two. 
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年7月5日-下午5:25:37
 *
 */
public class PowerOfTwo {
	/**
	 * bits of power of two:100000000....
	 * ... - 1:011111111111......
	 * make & operation
	 * @param n
	 * @return
	 */
	public boolean isPowerOfTwo(int n) {
        if(n <= 0) return false;
        return (n & (n-1)) == 0;
    }
	
	/**
	 * bits of power of two:100000000....
	 * ... - 1:011111111111......
	 * make & operation
	 * @param n
	 * @return
	 */
	public boolean isPowerOfTwo1(int n) {
		return n > 0 && Integer.bitCount(n) == 1;
    }
}
