package com.gohead.shared.test;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import java.util.ArrayList;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

/**
 * 参数化测试超类.
 * 
 * @author ChenJingShuai 2 Aug 2016
 *
 * @param <T> 期望结果/真实结果的类型
 */
@RunWith(Parameterized.class)
public class ParentTest<T> {
	protected static final Object OBJ = new Object();
	/**
	 * 期望的结果: true or false. null represents true
	 */
	protected Boolean expectedResult;
	
	/**
	 * 产生的Object
	 */
	protected T generatedObj;
	/**
	 * 期望的Object
	 */
	protected T expectedObj;
	/**
	 * Case Id
	 */
	protected int caseId;
	
	/**
	 * Control which case is executed,all cases are executed when its value is -1.
	 */
	protected static int debugNum = -1;
	
	/**
	 * Is ignored?
	 */
	protected boolean isIgnored = true;
	
	/**
	 * Inputed parameters
	 */
	protected List<Object> parameters = new ArrayList<>(5);
	
	public ParentTest(int caseId, boolean expectedResult, T expectedObj){
		this.caseId = caseId;
		this.expectedResult = expectedResult;
		this.expectedObj = expectedObj;
	}
	
	public ParentTest(int caseId, T expectedObj, boolean isIgnored){
		this.caseId = caseId;
		this.isIgnored = isIgnored;
		this.expectedObj = expectedObj;
		this.expectedResult = true;
	}

	public ParentTest(int caseId, boolean isIgnored, boolean expectedResult, T expectedObj){
		this(caseId, expectedResult, expectedObj);
		this.isIgnored = isIgnored;
	}
	
	/**
	 * 默认执行结果为true
	 * @param caseId
	 * @param expectedObj
	 */
	public ParentTest(int caseId, T expectedObj){
		this.caseId = caseId;
		this.expectedResult = true;
		this.expectedObj = expectedObj;
		this.expectedResult = true;
	}
	
	public ParentTest(){
		
	}
	
	public Boolean isExpectedResult() {
		return expectedResult;
	}

	public void setExpectedResult(boolean expectedResult) {
		this.expectedResult = expectedResult;
	}

	public T getExpectedObj() {
		return expectedObj;
	}

	public void setExpectedObj(T expectedObj) {
		this.expectedObj = expectedObj;
	}

	public int getCaseId() {
		return caseId;
	}

	public void setCaseId(int caseId) {
		this.caseId = caseId;
	}

	public boolean isIgnored() {
		return isIgnored;
	}

	public void setIgnored(boolean isIgnored) {
		this.isIgnored = isIgnored;
	}

	public List<Object> getParameters() {
		return parameters;
	}

	public void setParameters(List<Object> parameters) {
		this.parameters = parameters;
	}
	
	/**
	 * 设置Debug模式;-1代表关闭debug,大于0代表执行指定{@linkplain #caseId}的case.
	 * @param debugNum
	 */
	public void setDebug(int debugNum){
		ParentTest.debugNum = debugNum;
		if(!isIgnored && (debugNum == -1 || caseId == debugNum)){
			isIgnored = false;
		}
	}
	
	@Before
	public void before(){
		System.out.println("Case: " + caseId + " start...");
	}
	
	@After
	public void after(){
		if(!isIgnored){
			if(expectedObj.getClass().isArray()){
				Class<?> componentClazz = expectedObj.getClass().getComponentType();
				if(componentClazz == double.class){
					assertArrayEquals((double[])expectedObj, (double[])generatedObj, 0);
				}else if(componentClazz == long.class){
					assertArrayEquals((long[])expectedObj, (long[])generatedObj);
				}else if(componentClazz == byte.class){
					assertArrayEquals((byte[])expectedObj, (byte[])generatedObj);
				}else if(componentClazz == int.class){
					assertArrayEquals((int[])expectedObj, (int[])generatedObj);
				}else if(componentClazz == short.class){
					assertArrayEquals((short[])expectedObj, (short[])generatedObj);
				}else if(componentClazz == boolean.class){
					assertArrayEquals((boolean[])expectedObj, (boolean[])generatedObj);
				}else if(componentClazz == float.class){
					assertArrayEquals((float[])expectedObj, (float[])generatedObj, 0);
				}else{
					assertArrayEquals((Object[])expectedObj, (Object[])generatedObj);
				}
				System.out.println("Correct(array)...");
			}else{
				if(!expectedResult){
					if(null == expectedObj){ // 只需要判断生成的对象为空即可
						assertEquals(null, generatedObj); 
					}else{
						assertNotEquals(expectedObj, generatedObj);
					}
					System.out.println("Expected result is false and result is false...");
				}else{
					if(OBJ == expectedObj){ // 只需要判断生成的对象不为空即可
						assertNotEquals(null, generatedObj); 
					}else{
						assertEquals(expectedObj, generatedObj);
					}
					System.out.println("Expected result is true and result is true...");
				}
			}
		}else{
			System.out.println("Case " + caseId + " is ignored!"); 
		}
		System.out.println("Case: " + caseId + " end...");
	}

}
