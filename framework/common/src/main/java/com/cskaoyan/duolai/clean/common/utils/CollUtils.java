package com.cskaoyan.duolai.clean.common.utils;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.collection.IterUtil;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 继承自 hutool 的集合工具类
 */
public class CollUtils extends CollectionUtil {

    public static <T> List<T> emptyList() {
        return Collections.emptyList();
    }

    /**
     * 将元素加入到集合中，为null的过滤掉
     *
     * @param list 集合
     * @param data 要添加的数据
     * @param <T>  元素类型
     */
    public static <T> void add(Collection<T> list, T... data) {
        if (list == null || data.length == 0) {
            return;
        }
        for (T t : data) {
            if (ObjectUtils.isNotEmpty(t)) {
                list.add(t);
            }
        }
    }


    /**
     * 获取集合中某一列的值集合
     *
     * @param list
     * @param function
     * @return
     * @param <T>
     * @param <R>
     */
    public static <T,R> List<R> getFieldValues(List<T> list, Function<T,R> function) {
        if(isEmpty(list)){
            return null;
        }
        return list.stream().map(function)
                .collect(Collectors.toList());
    }


    public static <K,V> Map<K,V> defaultIfEmpty(Map<K,V> originMap, Map<K,V> defaultMap) {
        return CollUtils.isEmpty(originMap) ? defaultMap : originMap;
    }
}
