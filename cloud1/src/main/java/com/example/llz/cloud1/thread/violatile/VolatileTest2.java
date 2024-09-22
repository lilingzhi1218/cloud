package com.example.llz.cloud1.thread.violatile;

public class VolatileTest2 {
	private volatile int i = 0;

	public static void main(String[] args) throws InterruptedException {
		VolatileTest2 volatileTest = new VolatileTest2();
		new Thread(()->volatileTest.i++).start();
		new Thread(()-> volatileTest.i++).start();

		System.out.println(volatileTest.i);//=1
		/**
		 * i由两个线程分别自增操作，虽然加了volatile关键字（各线程修改了i的值，其他线程读取i时得到最新的i）
		 * 但依旧只等于1
		 * 原因是：volatile关键字只保证可见性，不保证操作的原子性，意思是i的读取和修改可能会分割执行
		 *
 		 */


	}

}
