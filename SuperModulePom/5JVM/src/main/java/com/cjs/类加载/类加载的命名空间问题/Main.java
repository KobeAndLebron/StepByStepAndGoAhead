package com.cjs.类加载.类加载的命名空间问题;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Modifier;

// 自己写的类加载器
public class Main extends ClassLoader{
	private String classPath = "";
	
	public Main(){
		
	}
	
	public Main(String classPath){
		// 默认调用父类的构造器，在父类的空构造器中，将系统类加载器设为其父类加载器
		super();
		this.classPath = classPath;
	}
	
	public Main(String classPath , ClassLoader parent){
		super(parent);
		this.classPath = classPath;
	}
	
	public Class<?> findClass(String clazzName){
		Class<?> clazz = null;
		
		byte[] byteArray = getByteArrayByClazzName(clazzName);
		
		if(byteArray != null){
			clazz = defineClass(clazzName, byteArray, 0, byteArray.length);
		}else{
			throw new RuntimeException(clazzName + "对应的二进制流没找到");
		}
		
		return clazz;
	}
	
	// 根据类的全限定名来获取对应的二进制流
	private byte[] getByteArrayByClazzName(final String clazzName){
		byte[] byteArray = null;
		
		String clazzFilePath = this.classPath + clazzName.replace('.', '/') + ".class";
		InputStream is = null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		try {
			is = new FileInputStream(clazzFilePath);
			byte[] tempByteArray = new byte[2048];
			int size = 0;
			while((size = is.read(tempByteArray)) > 0){
				baos.write(tempByteArray, 0, size);
			}
			byteArray = baos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return byteArray;
	}
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException {
		//LoadedCalssByMainClassLoader.test();
		
		System.out.println(System.getProperty("user.dir"));
		Main mainClassLoader = new Main(System.getProperty("user.dir") + "/test/");
		Class<?> loadedCalssByMainClass = mainClassLoader.loadClass("com.cjs.类加载的命名空间问题.LoadedCalssByMainClassLoader");
		
//		System.out.println(Modifier.toString(loadedCalssByMainClass.getConstructors()[0].getModifiers()));
		System.out.println("Main类的类加载器:" + Main.class.getClassLoader());
		System.out.println("LoadedCalssByMainClassLoader类的类加载器:" + loadedCalssByMainClass.getClassLoader());
		System.out.println("LoadedCalssByMainClassLoader类的父类加载器:" + loadedCalssByMainClass.getClassLoader().getParent());
		Object obj = loadedCalssByMainClass.newInstance();
		
		System.out.println(obj);
		
	}
}

class LoadedCalssByMainClassLoader{
	
	
	public LoadedCalssByMainClassLoader(){
		System.out.println(this.getClass().getName() + "被实例化");
	}
	
	public static void test1(){
		System.out.println(Main1.i);
	}
	
	public static void test() throws ClassNotFoundException, InstantiationException, IllegalAccessException{
		System.out.println("用户的项目目录: " + System.getProperty("user.dir"));
		Main mainClassLoader = new Main(System.getProperty("user.dir") + "/test/" , null);
		Class<?> loadedCalssByMainClassLoader = mainClassLoader.loadClass("com.cjs.类加载的命名空间问题.LoadedCalssByMainClassLoader");
		
		// 被不同类加载器所加载的类是不想等的
		System.out.println(LoadedCalssByMainClassLoader.class.equals(loadedCalssByMainClassLoader));
		System.out.println("Main类的类加载器:" + Main.class.getClassLoader());
		
		System.out.println("LoadedCalssByMainClassLoader类的类加载器:" + loadedCalssByMainClassLoader.getClassLoader());
		System.out.println("LoadedCalssByMainClassLoader类的父类加载器:" + loadedCalssByMainClassLoader.getClassLoader().getParent());
		Object obj = loadedCalssByMainClassLoader.newInstance();
		
		System.out.println(obj);
	}
}
