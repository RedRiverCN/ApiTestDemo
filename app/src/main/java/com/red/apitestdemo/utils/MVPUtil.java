package com.red.apitestdemo.utils;

import java.lang.reflect.ParameterizedType;

/**
 * Created by Red on 2017/6/24.
 */

public class MVPUtil {

    public static <T> T getT(Object o, int i) {
        try {
            return ((Class<T>) ((ParameterizedType) (o.getClass()
                    .getGenericSuperclass())).getActualTypeArguments()[i])
                    .newInstance();
        } catch (InstantiationException | ClassCastException | IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

}
