package com.example.llz.cloud1.proxy.staticProxy;

/**
 * 代理测试练习类
 *
 * 1.静态代理：
 * 1-1（继承）代理对象继承目标对象，重写目标对象的方法
 * 1-2（聚合）代理对象实现目标对象接口，将目标对象实例通过构造方法传递进去，重写目标对象的方法，可以在目标对象方法前后加逻辑
 * 2.动态代理
 * 2-1：创建代理类
 * 2-2：实现InvocationHandler，重写invoke方法,
 * 2-3：写一个getProxyInstance的方法，内部调用Proxy的静态方法newProxyInstance，可以返回当前类的代理对象
 *
 */
public class ProxyTest {
    public static void main(String[] args) {
        Julie julie = new Julie();
        JulieProxy julieProxy = new JulieProxy(julie);
        Star proxyInstance = (Star)julieProxy.getProxyInstance();
        proxyInstance.concert();
    }
}
