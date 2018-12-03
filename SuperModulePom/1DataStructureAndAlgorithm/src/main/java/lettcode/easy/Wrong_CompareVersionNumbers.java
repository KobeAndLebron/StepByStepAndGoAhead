package lettcode.easy;

/**
 * 
 * 错误的解法
 * 	1. 没有考虑0为前缀的时候,eg: 00012 < 13,但是按照此解法是错误的,因为它只算位数了.
 *  2. 当输入为""的时候,Integer.parseInt("")会报错,应该将其转化为0.
 *  3. 没考虑version1和version2为null的情况.
 * 
 * @see {@linkplain CompareVersionNumbers true solution}
 * 
 * @author ChenJingShuai  
 *
 * Make a bit of progress every day. 2016年8月6日-上午11:04:17
 *
 */
public class Wrong_CompareVersionNumbers {
	public int compareVersion(String version1, String version2) {
		int i = 0;
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		// whether i of version1 encounter point
		boolean ifEncounterPoint1 = false;
		// whether i of version2 encounter point
		boolean ifEncounterPoint2 = false;

		while(true){
			if(ifEncounterPoint1 && ifEncounterPoint2){ // 如果两者都遇到.或者都到结尾,那么就比较两者大小
				int temp =  compareNumberStr(sb1.toString(), sb2.toString());
				if(temp != 0){
					return temp;
				}
			}else if(ifEncounterPoint1){ // version2 is bigger
				return -1;
			}else if(ifEncounterPoint2){ // version1 is bigger
				return 1;
			}// 都没遇到.就继续
			
			char c1 = version1.charAt(i);
			if(c1 != '.'){ // 放数
				sb1.append(c1);
			}else{ // 一个版本号的终结
				ifEncounterPoint1 = true;
			}
			
			char c2 = version2.charAt(i);
			if(c2 != '.'){ // 放数
				sb2.append(c2);
			}else{ // 一个版本号的终结
				ifEncounterPoint2 = true;
			}
			i++;
			if(!(i < version1.length() && i < version2.length())){ // 至少有一个到头了
				// 1到头说明2的位数多,2大;反之一样;都到头说明位数一样,进行比较即可
				return (i < version1.length() ? 1 : 
					(i < version2.length() ? -1 : compareNumberStr(sb1.toString(), sb2.toString())));
			}
		}
    }
	
	private static int compareNumberStr(String str1, String str2){
		str1 = "".equals(str1) ? "0" : str1;
		str2 = "".equals(str2) ? "0" : str2;
		return (Integer.parseInt(str1) > Integer.parseInt(str2) ? 1 : 
			(Integer.parseInt(str1) < Integer.parseInt(str2) ? -1 : 0));
	}
}
