package com.cskaoyan.duolai.clean.common.utils;

import cn.hutool.core.util.ObjectUtil;

import java.util.function.Function;

/**
 * Object操作工具
 **/
public class ObjectUtils extends ObjectUtil {

    /**
     * 获取对象t的某个字段值
     *
     * @param t 获取对象t
     * @param function labda表达式，例如Orders::get
     * @return 对象t的某个字段值
     * @param <T> 对象t的类型
     * @param <R> 对象t对应字段的类型
     */
    public static <T,R> R get(T t, Function<T,R> function) {
        if(t == null) {
            return null;
        }
        return function.apply(t);
    }


}
