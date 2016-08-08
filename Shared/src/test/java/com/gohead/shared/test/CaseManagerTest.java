package com.gohead.shared.test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.junit.runners.Parameterized.Parameters;

public class CaseManagerTest extends ParentTest<List<Object>>{
	private String str;
	private int inte;
	private boolean flag;
	private List<Object> list;
	private Map<Object, Object> map;
	private List<Data> data;
	
	public CaseManagerTest(int caseId, boolean isIgnored, List<Object> expectedObj, String str, int inte, boolean flag, 
			List<Object> list, Map<Object, Object> map, List<Data> data){
		super(caseId, expectedObj, isIgnored);
		this.str = str;
		this.inte = inte;
		this.flag = flag;
		this.list = list;
		this.map = map;
		this.data = data;
	}
	
	@Parameters
	public static Collection<Object[]> generateParameters(){
		return Arrays.asList(CaseManager.getParameterArray("test.html"));
	}
	
	@Test
	public void test(){
		setDebug(-1);
		if(!isIgnored){
			this.generatedObj = new ArrayList<>();
			this.generatedObj.add(str);
			this.generatedObj.add(inte);
			this.generatedObj.add(flag);
			this.generatedObj.add(list);
			this.generatedObj.add(map);
			this.generatedObj.add(data);
		}
	}
}
