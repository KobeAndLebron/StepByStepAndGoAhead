package com.cjs.notSafeThreadExamples.third_atomicity;

/**
 * 用以证明++i的非原子性
 * 
 * @author ChenJingShuai
 *
 * 每天进步一点-2016年4月16日-下午8:27:07
 */
public class SerialNumberChecker {
	private static final int SIZE = 10;
	private static CircularSet serials = new CircularSet(1000);
	
	/**
	 * 用以保证{@linkplain SerialNumberGenerator}的正确性
	 * @author ChenJingShuai
	 *
	 * 每天进步一点-2016年4月16日-下午8:47:13
	 */
	static class SerialChecker implements Runnable{
		public void run(){
			while(true){
				/**
				 * 需要保证nextSerialNumber这个方法的线程安全，即serial的不重复性+++++++
				 */
				int serial = SerialNumberGenerator.nextSerialNumber();
				/**
				 * 所有线程产生的serialNumber都存进Heap堆内存
				 * 保证contain和add组合线程安全+++++++
				 */
				if(serials.contains(serial)){
					System.out.println("Duplicate: " + serial);
					System.exit(0);
				}
				serials.add(serial);
			}
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		SerialChecker s = new SerialChecker();
		for(int i = 0; i < SIZE; i++){
			Thread t = new Thread(s);
			t.setDaemon(true);
			t.start();
		}
		// Stop after n seconds if there is an argument
		if(args.length > 0){
			Thread.sleep(Long.valueOf(args[0]));
			System.out.println("No duplicates detected");
		}
	}
}
