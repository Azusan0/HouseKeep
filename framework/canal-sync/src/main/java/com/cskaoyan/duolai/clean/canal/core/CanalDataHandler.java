package com.cskaoyan.duolai.clean.canal.core;

import java.util.List;

/**
 *
 * @param <T>
 */
public interface CanalDataHandler<T> {

    /**
     * 批量保存
     * @param data
     */
    void batchSave(List<T> data);

    /**
     * 批量删除
     * @param ids
     */
    void batchDelete(List<Long> ids);
}
