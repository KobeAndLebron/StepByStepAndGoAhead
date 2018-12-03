package lettcode.easy;

/**
 * Write a function that takes a string as input and returns the string reversed.
 *
 * Example:
 * Given s = "hello", return "olleh". 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年7月6日-下午10:37:50
 */
public class ReverseString {
	public String reverseString(String s) {
		if(s == null || s.length() <= 1){
        	return s;
        } 
        char[] charArray = s.toCharArray();
        int length = charArray.length;
        char[] desArray = new char[length];
        for(int i = 0; i < length; i++){
        	desArray[length - i - 1] = charArray[i];
        }
        return new String(desArray, 0, length);
    }
	
	public String reverseString1(String s) {
        if(s == null || s.length() <= 1){
        	return s;
        } 
        char[] charArray = s.toCharArray();
        int length = charArray.length;
        for(int i = 0; i <= length/2 - 1; i++){
        	int index = length - i - 1;
        	char swapTemp = charArray[i];
        	charArray[i] = charArray[index];
        	charArray[index] = swapTemp;
        }
        return new String(charArray, 0, length);
    }
}
