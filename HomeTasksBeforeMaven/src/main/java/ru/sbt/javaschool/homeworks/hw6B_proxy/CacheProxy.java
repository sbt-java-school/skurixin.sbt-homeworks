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

    private HashMap<CacheValue, Object> cacheMap = new HashMap();

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("Get request");
        Object result;
        if (method.isAnnotationPresent(Cache.class)) {
            System.out.println("Cached method");
            CacheValue cacheValue = new CacheValue(method, args);
            result = searchInMap(cacheValue);
            if (result == null) {
                System.out.println("Put value in cache");
                result = method.invoke(delegate, args);
                cacheMap.put(cacheValue, result);
            } else {
                System.out.println("Get value from cache");
            }
        } else {
            System.out.println("Not cached method");
            result = method.invoke(delegate, args);
        }
        System.out.println("Request is done, result " + result.toString() + "\n");
        return result;
    }

    private Object searchInMap(CacheValue cacheValue) {
        for (CacheValue value : cacheMap.keySet()) {
            if (value.equals(cacheValue)) {
                return cacheMap.get(value);
            }
        }
        return null;
    }

    class CacheValue {
        private Method method;
        private Object[] args;

        public CacheValue(Method method, Object[] args) {
            this.method = method;
            this.args = args;
        }

        @Override
        public boolean equals(Object obj) {
            if (obj == this) return true;
            if (obj == null || obj.getClass() != getClass()) return false;
            if (((CacheValue) obj).method.equals(this.method)) {
                for (int i = 0; i < args.length; i++) {
                    Object arg = args[i];
                    if (!arg.equals(((CacheValue) obj).args[i])) {
                        return false;
                    }
                }
            }
            return true;
        }

        @Override
        public int hashCode() {
            int result=this.method.hashCode();
            for (Object arg : args) {
                result+=arg.hashCode();
            }
            return result;
        }
    }
}
