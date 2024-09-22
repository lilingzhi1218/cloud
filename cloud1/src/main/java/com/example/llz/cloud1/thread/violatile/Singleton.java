package com.example.llz.cloud1.thread.violatile;

/**
 * 为什么要给instance加上volatile关键字呢？
 * volatile可以防止指令排
 * 主要new Singleton() JVM做三件事
 * 1.给instance分配内存；2.调用无参构造初始化成员变量；3.将instance对象指向分配的内存空间
 * 由于JVM即时编译器中存在指令重排的优化行为，2和3的操作会不按顺序执行
 * 在执行了第3步还未执行第2步时，被别的线程getInstance()此时成员变量还没有初始化，得到的对象是不完整的
 */
public class Singleton {
    private volatile static Singleton instance = null;

    private Singleton(){}

    public static Singleton getInstance(){
        if (instance == null){
            synchronized (Singleton.class){
                if (instance == null){
                    instance = new Singleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) throws InterruptedException {
        for (int i = 0; i < 10; i++) {
            new Thread(()->System.out.println(Singleton.getInstance())).start();
        }
        Thread.sleep(1000);
    }
}
