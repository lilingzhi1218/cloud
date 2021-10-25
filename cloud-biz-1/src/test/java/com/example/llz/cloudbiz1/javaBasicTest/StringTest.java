package com.example.llz.cloudbiz1.javaBasicTest;

public class StringTest {
    public static void main(String[] args) {
        test();
    }

    /**
     * 测试String的引用
     */
    static void test(){
        String s1 = "abc";
        String s2 = "a";
        String s3 = s2 + "bc";
        String s4 = "a" + "bc";
        String s5 = s3.intern();

        System.out.println(s1 == s3);//s1引用的是常量池中的"abc",由于s2是个变量，不知道运行后会不会改变，所以无法在编译期间将s3的值计算出来，因此s3引用的是堆中的"abc"
        System.out.println(s1 == s4);//s4赋值号右边是常量表达式，所以在编译期间会优化为"abc",由于"abc"已经存在与常量池，则s3引用的是常量池中的"abc"
        System.out.println(s1 == s5);//intern会首先到字符串常量池检索是否已经存在字面值为“abc”的对象，不存在则添加到常量池，否则返回直接返回字符串常量的引用
    }
}
