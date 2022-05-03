package com.example.llz.cloud1.basic.abstracts;

public class Extend<E> extends Abstract<E> {
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
        return 0;
    }

    void func(E elm){
        this.put(elm);
    }

    public static void main(String[] args) {
        Extend<String> stringExtend = new Extend<>();
        stringExtend.put("asd");
    }
}
