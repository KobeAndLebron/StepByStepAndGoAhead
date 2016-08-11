package com.cjs.类加载.类加载的第一个过程装载;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

/**
 * 类加载的第一个过程：装载
 * 
 * 此阶段虚拟机完成三件事：
 * 1、根据一个类的全限定名来获取定义此类的二进制字节流
 * 2、将这个字节流所代表的静态存储结构转换为方法区的运行时数据结构
 * 3、在Java堆中生成一个代表这个类的Java.lang.Class对象，作为方法区的访问入口
 * 
 * 加载阶段可以使用系统的类加载器，也可以使用由用户自定义的类加载器来完成。
 * 加载阶段与连接阶段（eg：一部分字节码文件格式的验证动作）是交叉进行的，加载尚未完成，
 * 可能连接阶段已经开始。
 * 
 * @author 陈景帅
 *
 */
public class ClassLoader1 {
	public static void main(String[] args) throws ClassNotFoundException {
		ClassLoader threadClassLoader = Thread.currentThread().getContextClassLoader();
		System.out.println(threadClassLoader);
		String path = "C:\\Users\\Chen\\Desktop\\Src\\";
		String path1 = "C:\\Users\\Chen\\Desktop\\START java";
		ClassLoader classLoader1 = new MyUrlClassLoader1(path , null);
		ClassLoader classLoader = new MyUrlClassLoader1(path1 , classLoader1);
		// 双亲委派机制的好处：保证rt.jar库的安全性，使程序里面的Java.lang.String一定是此包的
		// 使得类加载器和类加载器所加载的类具有相同地位的优先级
		// Class<?> c = classLoader.loadClass("java.lang.String");
		// System.out.println(c.getClassLoader()); // null
		Class<?> c1 = classLoader.loadClass("Test");
		System.out.println(c1.getClassLoader());
		
	}
}

class MyUrlClassLoader1 extends ClassLoader{
	private String urlPath;
	// 路径的斜杠方向  
	private char pathDirection;
	
	public MyUrlClassLoader1(String urlPath){
		super();
		this.urlPath = urlPath;
		this.pathDirection = urlPath.contains("\\") ? '\\' : '/';
	}
	
	public MyUrlClassLoader1(String urlPath , ClassLoader par){
		super(par);
		this.urlPath = urlPath;
		this.pathDirection = urlPath.contains("\\") ? '\\' : '/';
	}
	public final Class<?> findClass(String clazzName) {
		String clazzPath = getClazzPath(clazzName);
		clazzPath = urlPath + (urlPath.endsWith(String.valueOf(pathDirection)) ? "" : pathDirection) + clazzName + ".class";
		InputStream is = null; 
		ByteArrayOutputStream baos = null;
		byte[] byteArray = null;
		try{
			is = new FileInputStream(clazzPath);
			baos = new ByteArrayOutputStream();
			int byteCode = 0;
			// 可以在这进行解密操作，这样极大丰富了Java的Class文件的形式，比如jar
			while((byteCode = is.read()) != -1){
				baos.write(byteCode);
			}
			byteArray = baos.toByteArray();
		}catch(FileNotFoundException fnf){
			// fnf.printStackTrace();
		}catch(IOException e){
			e.printStackTrace();
		}finally{
			if(is != null){
				try {
					is.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return byteArray != null ? defineClass(clazzName, byteArray, 0, byteArray.length) : null;
	}

	private String getClazzPath(String clazzName) {
		return clazzName.replace('.', this.pathDirection);
	}
	
	public String toString(){
		return this.getClass().getName() + " : " + this.urlPath;
	}
}
