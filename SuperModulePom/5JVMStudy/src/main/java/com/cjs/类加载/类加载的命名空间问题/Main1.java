package com.cjs.类加载.类加载的命名空间问题;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

// 自己写的类加载器
public class Main1 extends ClassLoader{
	// 类路径
	private String classPath = "";
	static int i = 1;
	
	{
		// 非静态代码块和构造器还有初始化赋值语句会按照顺序执行，非静态代码块和构造器会合并形成init()方法
	}
	
	// 并不负责开辟对象的内存空间和返回对象引用，只是负责对变量的赋值操作
	public Main1(){
		super();
	}
	
	public Main1(String classPath){
		// 默认调用父类的构造器，在父类的空构造器中，将系统类加载器设为其父类加载器
		super();
		this.classPath = classPath;
	}
	
	public Main1(String classPath , ClassLoader parent){
		super(parent);
		this.classPath = classPath;
	}
	
	// 通过类的全限定名来寻找其对应的class文件，然后用二进制流读出来，最后将二进制流读进内存(方法区)，最后生成class对象
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
	
	public static void main(String[] args) throws ClassNotFoundException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		System.out.println("用户的项目目录: " + System.getProperty("user.dir"));
		Main1 mainClassLoader = new Main1(System.getProperty("user.dir") + "/test/");
		Class<?> loadedCalssByMainClass = mainClassLoader.loadClass("com.cjs.类加载的命名空间问题.LoadedCalssByMainClassLoader");
		
		Method method = loadedCalssByMainClass.getMethod("test1", new Class<?>[]{});
		method.setAccessible(true);
		method.invoke(loadedCalssByMainClass, null);
		
		System.out.println("Main类的类加载器:" + Main1.class.getClassLoader());
		System.out.println("LoadedCalssByMainClassLoader类的类加载器:" + loadedCalssByMainClass.getClassLoader());
		System.out.println("LoadedCalssByMainClassLoader类的父类加载器:" + loadedCalssByMainClass.getClassLoader().getParent());
		Constructor cons = loadedCalssByMainClass.getConstructor(null);
		Object obj = cons.newInstance();
		
		System.out.println(obj);
		
	}
}

