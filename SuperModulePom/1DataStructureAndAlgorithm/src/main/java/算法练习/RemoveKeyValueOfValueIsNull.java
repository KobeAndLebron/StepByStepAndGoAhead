package 算法练习;

/**
 * 移除value为null的键值对，除了key为‘value’的情况
 * @author ChenJingShuai
 *
 */
public class RemoveKeyValueOfValueIsNull {
	public static String remove(String jsonStr){
		char[] charSequence = jsonStr.toCharArray();
		char[] desCharSequence = new char[jsonStr.length()];
		int size = 0;
		
		int leftQuoteIndex = 0;
		boolean flag = true;
		for(int i = 0;i < charSequence.length; i++){
			if(charSequence[i] == '"'){
				leftQuoteIndex++;
			}
			
			desCharSequence[size++] = charSequence[i];
			
			if(leftQuoteIndex % 2 != 0 && flag){
				leftQuoteIndex = i;
				flag = false;
			}else if(leftQuoteIndex % 2 == 0 && (charSequence[i] != ',' && charSequence[i] != ',' && charSequence[i] != '}')){
				char[] nullSequence = new char[5];
				for(int j = 0; j < 5; j++){
					desCharSequence[size++] = charSequence[i];
					nullSequence[j] = charSequence[++i];
				}
				desCharSequence[size++] = charSequence[i];
				if(new String(nullSequence).equals(":null")){
					size -= (i - leftQuoteIndex + 1); 
				}else{
					while(charSequence[i] != ',' || charSequence[i] != '}' || 
							charSequence[i] != ']'){
						desCharSequence[size++] = charSequence[i++];
					}
					desCharSequence[size++] = charSequence[i];
				}
				
				flag = true;
			}
		}
		return new String(desCharSequence, 0, size);
	}
}
