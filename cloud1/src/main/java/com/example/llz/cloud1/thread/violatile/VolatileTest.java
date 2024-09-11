package com.example.llz.cloud1.thread.violatile;

public class VolatileTest {
	private volatile Boolean flag = true;

	public static void main(String[] args) {
		VolatileTest volatileTest = new VolatileTest();
		new Thread(()->{
			while (volatileTest.flag) {
				
			}
			System.out.println(Thread.currentThread().getName() + "线程结束");
		}).start();
		
		new Thread(()->{
			try {
				Thread.sleep(3000);
				volatileTest.updateFlag();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
	}

	private void updateFlag(){
		System.out.println("线程" + Thread.currentThread().getName() + "把flag的值更改成了false");
		flag = false;
	}
}
