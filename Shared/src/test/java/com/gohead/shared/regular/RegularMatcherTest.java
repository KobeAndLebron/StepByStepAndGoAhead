package com.gohead.shared.regular;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

import com.gohead.shared.test.ParentTest;

public class RegularMatcherTest extends ParentTest<Boolean>{
	private String inputStr;
	
	public RegularMatcherTest(int caseId, Boolean expectedObj, String inputStr){
		super(caseId, expectedObj);
		this.inputStr = inputStr;
	}
	
	@Parameters
	public static Collection<Object[]> generateParameters(){
		return Arrays.asList(new Object[][]{
			new Object[]{1, true, "int[1, 2, 3]"},
			new Object[]{2, true, "int[1]"}, 
			new Object[]{3, false, "int[]"},
			new Object[]{4, false, "int[1, 2, 3"},
			new Object[]{5, false, "int1, 2, 3]"},
			new Object[]{6, true, "int    [   1, 2, 3   ]   "},
			new Object[]{7, true, "com.gohead.shared.test.Data[{\"name\": \"test\"}, {\"name\": \"test1\"}]"}
			
		});
	}
	
	@Test
	public void commonTest(){
		setDebug(-1);
		if(!isIgnored){
			this.generatedObj = RegularMatcher.isArray(inputStr);
		}
	}
}
