package com.example.llz.cloud1.temp;

public interface InterfaceTest1 {
    //java8之后default关键字可以修饰在interface中的方法
    //使得方法可以在interface中实现其功能，实现了该接口的类的实例，可以直接调用改方法，无需再次实现
    //如果某个类实现的两个接口中存在同名方法，需要在该类重新实现,不管有没有default关键字修饰
    default void func(){
        
    }
    
    void function();
}
