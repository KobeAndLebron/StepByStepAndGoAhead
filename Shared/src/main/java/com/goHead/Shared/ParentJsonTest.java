package com.goHead.Shared;

import java.lang.reflect.Array;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ParentJsonTest<T> {
	protected boolean expectedResult;
	
	protected T generatedObj;
	protected T expectedObj;
	
	protected int caseId;
	
	protected static int debugNum = -1;
	
	@Before
	public void before(){
		System.out.println("Case: " + caseId + " start...");
	}
	
	@After
	public void after(){
		if(debugNum == -1 && caseId == debugNum){
			if(expectedObj.getClass() == Array.class){
				assertArrayEquals(generatedObj, generatedObj);
			}
		}
		System.out.println("Case: " + caseId + " end...");
	}
}
