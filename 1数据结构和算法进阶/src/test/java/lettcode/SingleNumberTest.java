package lettcode;

import java.util.Arrays;

import org.junit.Test;
import static org.junit.Assert.*;

public class SingleNumberTest {
	@Test
	public void test1(){
		int[] array = new int[]{1403617094,-490450406,-1756388866,-967931676,1878401007,1878401007,-74743538,1403617094,-1756388866,-74743538,-490450406,-1895772685};
		System.out.println(Arrays.toString(new SingleNumber().singleNumber(array)));
		
		try{
			assertArrayEquals(new int[]{-1895772685, -967931676}, new SingleNumber().singleNumber(array));
		}catch(AssertionError e){
			assertArrayEquals(new int[]{-967931676, -1895772685}, new SingleNumber().singleNumber(array));
		}
	}
	
	
	@Test
	public void test2(){
		int[] array = new int[]{0,1};
		System.out.println(Arrays.toString(new SingleNumber().singleNumber(array)));
		
		try{
			assertArrayEquals(new int[]{0, 1}, new SingleNumber().singleNumber(array));
		}catch(AssertionError e){
			assertArrayEquals(new int[]{1, 0}, new SingleNumber().singleNumber(array));
		}
	}
}
