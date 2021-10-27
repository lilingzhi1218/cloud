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
    /*
    * 1.String和StringBuffer主要区别是性能:String是不可变对象,每次对String类型进行操作都等同于产生了一个新的String对象,然后指向新的String对象.
    * 所以尽量不要对String进行大量的拼接操作,否则会产生很多临时对象,导致GC开始工作,影响系统性能.
    * 2.StringBuffer是对象本身操作,而不是产生新的对象,因此在有大量拼接的情况下,我们建议使用StringBuffer(线程安全).
    * 3.StringBuffer和StringBuilder的实现原理一样,其父类都是AbstractStringBuilder.StringBuffer是线程安全的,
    * StringBuilder是JDK 1.5新增的,其功能和StringBuffer类似,但是非线程安全.因此,在没有多线程问题的前提下,使用StringBuilder会取得更好的性能.
    * */
}
