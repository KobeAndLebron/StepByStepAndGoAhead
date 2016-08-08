package com.cjs.goHead;

import java.util.List;

import com.cjs.gohead.generic.classorinterface.Fibonacci;

/**
 *  Confusion:
 *  	1 List<?> cannot add Object
 *      2 Iterator Iterable  @see {@linkplain Fibonacci simple iterator}
 * 
 * 	TODO
 * 		1 How ArrayList implement ConcurrentModificationException ?
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年8月8日-下午9:58:56
 */
public class B2016_8_8 {
	List<?> list;
	List<? extends Object> list1;
	List<Object> list2;
	// List 等价于 List1,当不指定限定符时,默认为extends Object.
	
	{
		// list.add(new Object());  wrong!!!
		// list1.add(new Object()); wrong!!!
		list2.add(new Object());
		list.add(null);
		list1.add(null);
		list2.add(null);
	}
	
	
}
