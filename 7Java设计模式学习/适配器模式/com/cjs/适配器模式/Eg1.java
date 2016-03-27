package com.cjs.适配器模式;

/**
 * 本文讨论适配器模式。适配器模式是23中设计模式之一，它的主要作用是在新接口和老接口之间进行适配。它非常像我们出国旅行时带的电源转换器。
 * 为了举这个例子，我还特意去京东上搜了一下电源转换器，确实看到了很多地方的标准不一样。我们国家的电器使用普通的扁平两项或三项插头，而去外国的话，
 * 使用的标准就不一样了，比如德国，使用的是德国标准，是两项圆头的插头。如果去德国旅游，那么我们使用的手机充电器插头无法插到德国的插排中去，
 * 那就意味着我们无法给手机充电。怎样解决这个问题呢？只要使用一个电源转化器就行了。 
 * 
 * 用代码实现
 * 
 * @author 陈景帅
 *
 */
public class Eg1 {
	public static void main(String[] args) {
		GerManSocket gms = new GerManSocket1();
		if(gms.power("round")){
			System.out.println("success");
		}else{
			System.out.println("fail");
		}
		
		if(gms.power("flat")){
			System.out.println("success");
		}else{
			System.out.println("fail");
		}
		
		/************这时候就需要适配器了*******************/
	
		Adapter a = new Adapter1(gms);
		a.power("flat");
	
	
	}
}	

// 德国的插座标准,这个标准是不可能变得！！！
interface GerManSocket{
	static final String str = "round";
	
	/**
	 * 用String来代表电器的插头类型
	 * @param appliance
	 * @return 是否冲上电了
	 */
	public boolean power(String appliance);
}

// 一个符合德国插座标准的插座
class GerManSocket1 implements GerManSocket{
	
	public boolean power(String appliance) {
		if(str.equals(appliance)){
			System.out.println("冲冲冲");
			return true;
		}
		return false;
	}
	
}

// 代表中国的电器插头对于德国的适配器
interface Adapter{
	static final String str = "flat";
	
	public boolean power(String appliance);
}

class Adapter1 implements Adapter{
	private GerManSocket gms;
	
	public Adapter1(GerManSocket gms){
		this.gms = gms;
	}
	
	public boolean power(String appliance) {
		if(appliance.equals(Adapter.str)){
			return gms.power("round");
		}
		return false;
	}
	
}