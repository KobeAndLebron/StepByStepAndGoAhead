package lettcode.easy;

/**
 * 		Compare two version numbers version1 and version2.
 *		If version1 > version2 return 1, if version1 < version2 return -1, otherwise return 0.
 *
 *		You may assume that the version strings are non-empty and contain only digits and the . character.
 *	The . character does not represent a decimal point and is used to separate number sequences.
 *		For instance, 2.5 is not "two and a half" or "half way to version three", it is the fifth second-level 
 *	revision of the second first-level revision.
 *
 *		Here is an example of version numbers ordering:
 *
 *		0.1 < 1.1 < 1.2 < 13.37
 */
public class CompareVersionNumbers {
	public int compareVersion(String version1, String version2) {
		int i = 0;
		int j = 0;
		StringBuffer sb1 = new StringBuffer();
		StringBuffer sb2 = new StringBuffer();
		// whether i of version1 encounter point
		boolean ifEncounterPoint1 = false;
		// whether i of version2 encounter point
		boolean ifEncounterPoint2 = false;

		while(i < version1.length() || j < version2.length()){
			if(!ifEncounterPoint1){
				char c1 = ' ';
				if(i >= version1.length() || ( c1 = version1.charAt(i)) == '.'){
					ifEncounterPoint1 = true;
				}else{
					if(c1 != '0' || sb1.length() != 0){
						sb1.append(c1);
					}
				}
				i++;
			}
			
			if(!ifEncounterPoint2){
				char c2 = ' ';
				if(j >= version2.length() || ( c2 = version2.charAt(j)) == '.'){
					ifEncounterPoint2 = true;
				}else{
					if(c2 != '0' || sb2.length() != 0){
						sb2.append(c2);
					}
				}
				j++;
			}
			
			if(ifEncounterPoint1 && ifEncounterPoint2){ // 如果两者都遇到.或者都到结尾,那么就比较两者大小
				int temp =  compareNumberStr(sb1.toString(), sb2.toString());
				if(temp != 0){
					return temp;
				}else{// 想等重置初始条件
					sb1 = new StringBuffer();
					sb2 = new StringBuffer(); // ********
					ifEncounterPoint2 = ifEncounterPoint1 = false;
				}
			}
		}
		return compareNumberStr(sb1.toString(), sb2.toString());
    }
	
	private static int compareNumberStr(String str1, String str2){
		str1 = "".equals(str1) ? "0" : str1;
		str2 = "".equals(str2) ? "0" : str2; // *************
		return (Integer.parseInt(str1) > Integer.parseInt(str2) ? 1 : 
			(Integer.parseInt(str1) < Integer.parseInt(str2) ? -1 : 0));
	}
}
