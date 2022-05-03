package com.example.llz.cloud1.basic.abstracts;

public  abstract class Abstract<E> {
    public abstract E get(int index);

    public abstract int size();

    public void put(E elm){
        System.out.println(elm);
    }
}
