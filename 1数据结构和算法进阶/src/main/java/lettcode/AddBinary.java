package lettcode;
/**
 * Given two binary strings,return their sum(also a binary string)
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月14日-下午8:20:54
 */
public class AddBinary {
	public String addBinary(String a, String b) {
		// 判断参数有效性
		if(a == null || b == null){
	        return null;
	    }
		char[] ac = a.toCharArray();
		char[] bc = b.toCharArray();
		
		// sum array length
		int rcLength = (ac.length > bc.length ? ac.length : bc.length) + 1;
		char[] rc = new char[rcLength];
		int index = rc.length;
		
		// 上一位是否有进位，初始为false
		boolean flag = false;
		for(int i = ac.length - 1, j = bc.length - 1;i >= 0 || j >= 0;i--, j--){
			char ic = i >= 0 ? ac[i] : '0';
			char jc = j >= 0 ? bc[j] : '0';
			
			if(ic == jc){ 
				if(ic == '1'){ // 都为1
					if(flag){
						rc[--index] = '1';
					}else{
						rc[--index] = '0';
					}
					flag = true;
				}else{ // 都为0
					if(flag){ 
						rc[--index] = '1';
					}else{
						rc[--index] = '0';
					}
					flag = false;
				}
			}else{ // 10或01的关系
				if(flag){
					rc[--index] = '0';
					flag = true;
				}else{
					rc[--index] = '1';
					flag = false;
				}
			}		
		}
		if(flag){
			rc[--index] = '1';
		}
		return new String(rc, index, rcLength - index);
	}
}
