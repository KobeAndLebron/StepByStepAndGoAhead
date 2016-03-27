package 算法练习;

/**
 * 模仿{@link java.lang.String}的startsWith方法
 * @author 陈景帅
 *
 */
public class 判断一个字符串是否是一个字符串的前缀 {
	public static void main(String[] args) {
		System.out.println(starstWith("AA", "AA11"));
	}
	
	private static boolean starstWith(String targetStr , String prefix){
		return startsWith(targetStr , prefix , 0);
	}

	private static boolean startsWith(String targetStr, String prefix, int startOfSub) {
		char[] tar = targetStr.toCharArray();
		int start = 0;
		int tarLength = tar.length;
		
		char[] pre = prefix.toCharArray();
		int preLength = pre.length;
		
		
		if(startOfSub < 0 || (pre.length > tarLength - startOfSub)){
			return false;
		}
		
		while(--preLength > 0){
			if(pre[start] != tar[startOfSub + start++]){
				return false;
			}
		}
		return true;
	}
	
}
