package com.cskaoyan.duolai.clean.common.utils;

import cn.hutool.core.util.NumberUtil;

import java.math.BigDecimal;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class NumberUtils extends NumberUtil {


    /**
     * 如果number为空，将number转换为0，否则原数字返回
     *
     * @param number 原数值
     * @return 整型数字，0或原数字
     */
    public static Integer null2Zero(Integer number){
        return number == null ? 0 : number;
    }



    /**
     * 如果number为空，将number转换为0L，否则原数字返回
     *
     * @param number  原数值
     * @return 长整型数字，0L或原数字
     */
    public static Long null2Zero(Long number){
        return number == null ? 0L : number;
    }


    /**
     * 比较两个数字是否相同，
     * @param number1 数值1
     * @param number2 数值2
     * @return 是否一致
     */
    public static boolean equals(Integer number1, Integer number2) {
        if(number1 == null || number2 == null){
            return false;
        }
        return number1.equals(number2);
    }






    public static Integer null2Default(Integer originNumber, int defaultNumber) {
        return originNumber == null ? defaultNumber : originNumber;
    }

}
