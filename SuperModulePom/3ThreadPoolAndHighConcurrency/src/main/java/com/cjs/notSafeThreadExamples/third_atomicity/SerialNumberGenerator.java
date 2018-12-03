package com.cjs.notSafeThreadExamples.third_atomicity;

/**
 * 序列化数字产生器
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-下午8:26:39
 */
public class SerialNumberGenerator {
	private static int serialNumber = 0;
	
	/**
	 * 对于i++和i = i + 1操作来说,映射到.class文件里:<br/>
	 * first: getfield <br/>
	 * second: iconst_1 <br/>
	 * third: iadd <br/>
	 * four: putfield <br/>
	 * <br/>
	 * 在get和put之间是两个操作，所以这个操作不是原子性的，可能产生两个一样的值;
	 * 并且由于serialNumber是static的，所以需要考虑线程安全问题+++++++
	 *
	 * @return the next serialNumber
	 */
	public synchronized static int nextSerialNumber(){
		return serialNumber++;
	}
}
