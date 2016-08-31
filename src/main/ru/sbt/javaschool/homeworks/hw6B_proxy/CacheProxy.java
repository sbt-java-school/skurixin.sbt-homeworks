package ru.sbt.javaschool.homeworks.hw6B_proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;

/**
 * Created by скурихин on 31.08.2016.
 */
public class CacheProxy implements InvocationHandler {
    private final Object delegate;

    public CacheProxy(Object delegate) {

        this.delegate = delegate;
    }

    private HashMap<CacheValue, Double> cacheMap = new HashMap();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Get request");
        Object result = method.invoke(delegate, args);
        System.out.println("Request is done, result " + result.toString());
        return result;
    }

    class CacheValue {
        private Method method;
        private Object[] args;

        public CacheValue(Method method, Object[] args) {
            this.method = method;
            this.args = args;
        }
    }
}
