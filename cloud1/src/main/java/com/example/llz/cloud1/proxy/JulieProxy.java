package com.example.llz.cloud1.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * 朱莉代理人
 */
public class JulieProxy implements InvocationHandler {

    private Star star;

    public JulieProxy(Star star) {
        this.star = star;
    }

    /**
     * @param proxy  the proxy instance that the method was invoked on
     * @param method the {@code Method} instance corresponding to
     *               the interface method invoked on the proxy instance.  The declaring
     *               class of the {@code Method} object will be the interface that
     *               the method was declared in, which may be a superinterface of the
     *               proxy interface that the proxy class inherits the method through.
     * @param args   an array of objects containing the values of the
     *               arguments passed in the method invocation on the proxy instance,
     *               or {@code null} if interface method takes no arguments.
     *               Arguments of primitive types are wrapped in instances of the
     *               appropriate primitive wrapper class, such as
     *               {@code java.lang.Integer} or {@code java.lang.Boolean}.
     * @return
     * @throws Throwable
     */
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        dosomthingBefore();
        Object invoke = method.invoke(star, args);
        doSomethingAfter();
        return invoke;
    }

    private void doSomethingAfter() {
        System.out.println("调用后的方法");
    }

    private void dosomthingBefore() {
        System.out.println("调用前的方法");
    }

    public Object getProxyInstance(){
        return Proxy.newProxyInstance(star.getClass().getClassLoader(), star.getClass().getInterfaces(), this);
    }
}
