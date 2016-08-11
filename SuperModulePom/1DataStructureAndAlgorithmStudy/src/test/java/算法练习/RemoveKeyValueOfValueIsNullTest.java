package 算法练习;

import java.io.IOException;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.junit.Test;

public class RemoveKeyValueOfValueIsNullTest {
	ObjectMapper objectMapper = new ObjectMapper();
	
	@Test
	public void ifKeyValueOfValueIsNullIsRemoved() throws JsonGenerationException, JsonMappingException, IOException{
		System.out.println(System.getProperty("java.class.path"));
		DataStream dataStream1 = new DataStream();
		dataStream1.setDevice("device1");
		dataStream1.setDataPointsCount(-1);
		
		String str = objectMapper.writeValueAsString(dataStream1);
		System.out.println(str);
		System.out.println(RemoveKeyValueOfValueIsNull.remove(str));
	}
}
