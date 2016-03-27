package com.cjs.字符集1;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.Charset;

/**
 * 每天进步一点
 * 今天学习字符集和字符编码
 * 
 * 计算机存储的都是0和1，这些0和1加上下文环境就可以用来表示视频、图片、文本等多种形式的文件。
 * 
 * 字符集：字符的集合
 * 字符编码：字符集中的每个字符都有数字与其相对应，字符编码就是对该数字进行编码，将编码后的结果01序列存入内存中
 * 内存：存储编码后的01序列，当编码和解码的规则不一样时，就会产生乱码
 * 
 * Example： Unicode编码集有多种编码规则，比如字符哈对应数字（code point）12，12经过不同的编码规则utf-8、utf-16之后存入内存的01序列也不一样
 * 所以必须编码和解码的规则一样时才不会产生乱码。
 * 
 * @author 陈景帅
 * 2015年12月29日
 */
public class 根据A文件 {
	public static void main(String[] args) throws IOException {
		/**
		 * file.encoding 表示程序进入main函数之后此main函数所在的文件采用的编码格式，
		 * javac -encoding如果所用的编码和此编码（文件的编码）不一样，就会编译错误，因为解析不了文件的01序列
		 * 
		 * file.encoding 的值只有一个, 并且值为入口函数的保存编码的值
		 * 
		 * eclipse 会自动的加上此文件的编码规则，而如果手动编译的话，-encoding会自动的使用操作系统所采用的编码规则。
		 *
		 * 编译之后，.class文件都是unicode编码格式
		 */
		
		System.out.println(System.getProperty("file.encoding"));
		System.out.println(Charset.defaultCharset());
		
		// 文件A采用UTF-8编码
		FileReader f = new FileReader("a.txt");
		InputStreamReader isr = new InputStreamReader(new FileInputStream("a.txt") , "GBK");
		@SuppressWarnings("resource")
		// 此时这个字节流字符流就是UTF-8流
		BufferedReader br = new BufferedReader(f);
		// 线程安全的
		StringBuffer sb = new StringBuffer();
		// 控制一行几个编号
		int i = 0;
		String s = "";
		while((s = br.readLine()) != null && s.length() != 0){
			i++;
			sb.append("'" + s + "'" + " , ");
			if(i % 5 == 0){
				sb.append("\n");
			}
		}
		
		// 用UTF-8解码之后，字符串正确，此时的字符串也是UTF-8编码后的01序列
		CharSequence s1 = sb.subSequence(0, sb.lastIndexOf(","));
		@SuppressWarnings("resource")
		FileOutputStream fis = new FileOutputStream("b.txt");
		// 对UTF-8编码后的01序列采用GBK进行编码，就会对UTF-8所形成的01序列解码错误，而产生乱码
		fis.write(s1.toString().getBytes("UTF-8"));
		// fis.write(s1.toString().getBytes("UTF-8")); 解码和编码的规则相同就不会产生乱码
		System.out.println(s1.toString());
	}
}
