package com.cjs.notSafeThreadExamples.third;

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
				 * 需要保证nextSerialNumber这个方法的线程安全
				 */
				int serial = SerialNumberGenerator.nextSerialNumber();
				if(serials.contains(serial)){
					System.out.println("Duplicate: " + serial);
					System.exit(0);
				}
				serials.add(serial);
			}
		}
	}
	
	public static void main(String[] args) throws NumberFormatException, InterruptedException {
		for(int i = 0; i < SIZE; i++){
			Thread t = new Thread(new SerialChecker());
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
