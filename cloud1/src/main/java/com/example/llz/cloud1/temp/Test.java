package com.example.llz.cloud1.temp;

public class Test<E> extends ExtendTest<E> implements InterfaceTest1, InterfaceTest<E>{
    @Override
    void abs() {
        
    }
    @Override
    public void function() {
    }

    //master---
    //----

    public static void main(String[] args) {
        Test<String> test =  new Test<>();
    }
    ///master-1
}
