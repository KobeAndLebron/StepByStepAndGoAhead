package com.cjs.enumStudy;

/**
 * An enum in java is just like any other class,with a predefined set of
 * instances,just so so. 
 * Function: Prevent the possibility of invalid
 * parameters,for example,imagine the following method :
 * public void doSomethingWithColor(int color); this is ambiguous,and other developers have
 * no idea how to use it,if you have an enum with BLACK,RED,BLUE,etc. the
 * signature becomes: 
 * public void doSomethingWithColor(Color color); 
 * Code calling the method will be more readable,and can't provide invalid
 * parameters.
 * 
 * Here are several simple examples to highlight how to use Java enum.
 * 
 * 由于枚举类型的实例都是常量，so by convention，they are displayed with upperCase letter；
 * 
 * 在创建enum的时候，编译器会自动添加一些有用的特性。
 * eg：会创建toString方法来显示X个enum常量的名字------
 * 创建ordinal方法来显示X个enum常量的声明顺序
 * 以及static values方法来桉声明顺序产生由这些常量组成的数组
 * 
 * @author jingshuai.chen
 *
 */
public class EnumStudy {
	public static void main(String[] args) {
		Color1[] colors = Color1.values();
		for (Color1 element : colors) {
			System.out.println(element.getValue());
		}
	
		//------
		for (Color2 element : Color2.values()) {
			System.out.println(element.toString() + ", it's number is " + element.ordinal());
		}
	}
}

// example 1:classes's constructor is not empty.
enum Color1 {
	// a predefined set of instances,and Color1 can't have any other instance.
	GREEN("aaa"), RED("aaa1"), BLUE("aaa2");

	private String value;

	Color1(String value) {
		this.value = value;
	}

	Color1() {
	}

	// define method which each instance in Color1 both have
	public String getValue() {
		return this.value;
	}
}


// example 2:classes's constructor is empty.
enum Color2 {
	GREEN, RED, BLUE;

	Color2() {
	}

}
