package 海量数据;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.junit.internal.Throwables;

public class ProduceNumbersOfNumber {
	public static void main(String[] args) {
		produceFileByNumber(1111);
	}
	
	public static void produceFileByNumber(int quantity){
		System.out.println(System.getProperty("java.class.path"));
		OutputStream os = null;
		
		try{  
			os = new FileOutputStream("test.txt");
			os.write(1);
		}catch(FileNotFoundException e){
			e.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}
		
		try{
			throw new RuntimeException();
		}finally{
			
		}
	}
	
	
}
