package com.cjs.enumStudy;

/**
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年3月26日-下午9:01:52
 */
public class EnumWithSwitch {
	public static void main(String[] args) {
		Color1 color1 = Color1.GREEN;
		Color1 color2 = Color1.BLUE;
		Color1 color3 = Color1.RED;
		switchColor(color1);
		switchColor(color2);
		switchColor(color3);
	}
	
	public static void switchColor(Color1 color){
		switch (color) {
		// 这种情况不受break的影响，当且仅当找不到case的时候才触发
		// 由于enum的作用，不能提供invalid parameter
		/*default:
			throw new RuntimeException("parameter is invalid");*/
		case GREEN: System.out.println("it is green");
			      	break;
		case BLUE:	System.out.println("it is blue");
					break; // 不加break的话会一直执行case直到遇到break块
		case RED:	System.out.println("it is red");
					break;
		
		}
	}
}
