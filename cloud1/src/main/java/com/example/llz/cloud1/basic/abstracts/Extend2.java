package com.example.llz.cloud1.basic.abstracts;

public class Extend2<E> extends Abstract<E> {
    /**
     * @param index index of the element to return
     * @return
     */
    @Override
    public E get(int index) {
        return null;
    }

    /**
     * @return
     */
    @Override
    public int size() {
        System.out.println("Extend2");
        return 0;
    }

    void func(E elm){
        this.put(elm);
    }
    
}
