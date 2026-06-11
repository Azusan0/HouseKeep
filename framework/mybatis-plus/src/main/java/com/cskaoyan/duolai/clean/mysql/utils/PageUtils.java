package com.cskaoyan.duolai.clean.mysql.utils;

import com.cskaoyan.duolai.clean.common.model.PageResult;
import com.cskaoyan.duolai.clean.common.model.dto.PageDTO;
import com.cskaoyan.duolai.clean.common.model.dto.PageRequest;
import com.cskaoyan.duolai.clean.common.utils.CollUtils;
import com.cskaoyan.duolai.clean.common.utils.ObjectUtils;
import com.cskaoyan.duolai.clean.common.utils.StringUtils;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.ArrayList;
import java.util.List;

/**
 * 分页工具
 **/
public class PageUtils {

    /**
     * mybatis的分页数据是否为空
     *
     * @param page
     * @return
     */
    public static boolean isEmpty(Page page) {
        return page == null || CollUtils.isEmpty(page.getRecords());
    }

    /**
     * 判断mybatis的分页数据不为空
     *
     * @param page
     * @return
     */
    public static boolean isNotEmpty(Page page) {
        return page != null && !CollUtils.isEmpty(page.getRecords());
    }

    /**
     * 分页数据转换返给其他微服务，主要场景是从数据库中查出来的数据转换成DTO，或者VO
     *
     * @param originPage  从数据库查询出来的分页数据
     * @param <X>         目标对象类型
     * @param <Y>         源对象类型
     * @return 用于传递的分页数据
     */
    public static <X, Y> PageDTO<Y> toPage(Page<X> originPage, List<Y> list) {
        if (isEmpty(originPage)) {
            return new PageDTO<>(0L, 0L, new ArrayList<>());
        }

        return new PageDTO<>(originPage.getPages(), originPage.getTotal(), list);
    }


    /**
     * 将前端传来的分页查询条件转换成数据库的查询page,
     * 如果进行排序必须填写targetClazz
     * 该方法支持简单的数据字段排序，不支持统计排序
     *
     * @param pageRequest 前端传来的查询条件
     * @param <T>          查询数据库po
     * @param targetClazz  校验数据库中是否有需要排序的字段
     * @return mybatis-plus 分页查询page
     */
    public static <T> Page<T> parsePageQuery(PageRequest pageRequest, Class<T> targetClazz) {
        Page<T> page = new Page<>(pageRequest.getPageNo(), pageRequest.getPageSize());
        //是否排序
        if (targetClazz != null) {
            List orderItems = getOrderItems(pageRequest, targetClazz);
            if (CollUtils.isNotEmpty(orderItems)) {
                page.addOrder(orderItems);
            }
        } else {
            //如果没有更新时间按照添加逆序排序
            OrderItem orderItem = new OrderItem();
            orderItem.setAsc(false);
            orderItem.setColumn("id");
            page.addOrder(orderItem);

        }
        return page;
    }

    public static <T> List<OrderItem> getOrderItems(PageRequest pageRequest, Class<T> targetClazz) {
        List<OrderItem> orderItems = new ArrayList<>();
        if (ObjectUtils.isEmpty(pageRequest)) {
            return orderItems;
        }
        // 排序字段1
        if (StringUtils.isNotEmpty(pageRequest.getOrderBy1())) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(StringUtils.toSymbolCase(pageRequest.getOrderBy1(), '_'));
            orderItem.setAsc(pageRequest.getIsAsc1());
            orderItems.add(orderItem);
        }
        // 排序字段2
        if (StringUtils.isNotEmpty(pageRequest.getOrderBy2())) {
            OrderItem orderItem = new OrderItem();
            orderItem.setColumn(StringUtils.toSymbolCase(pageRequest.getOrderBy2(), '_'));
            orderItem.setAsc(pageRequest.getIsAsc2());
            orderItems.add(orderItem);
        }
        return orderItems;
    }

}
