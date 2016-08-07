package com.gohead.shared.test;

import java.util.Arrays;
import java.util.Collection;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class CaseManagerTest extends ParentTest<Object[][]>{
	private String htmlPath;
	
	public CaseManagerTest(int caseId, boolean isIgnored, boolean expectedResult, Object[][] expectedObj, 
						String htmlPath){
		super(caseId, isIgnored, expectedResult, expectedObj);
		this.htmlPath = htmlPath;
	}
	
	@Parameters
	public static Collection<Object[]> generateParameters(){
		return Arrays.asList(new Object[][]{
			new Object[]{1, false, true, new Object[][]{new Object[]{1, false, true, null}},"test.html"
			}
		});
	}
	
	@Test
	public void test(){
		setDebug(-1);
		if(!isIgnored){
			this.generatedObj = CaseManager.getParameterArray(htmlPath);
		}
	}
}
