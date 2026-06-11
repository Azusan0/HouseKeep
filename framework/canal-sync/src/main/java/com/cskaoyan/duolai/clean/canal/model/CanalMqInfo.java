package com.cskaoyan.duolai.clean.canal.model;

import com.cskaoyan.duolai.clean.canal.constants.OperateType;
import lombok.Data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

/**
 *  “”  
 */
@Data
public class CanalMqInfo implements Serializable {

    private String database;
    private String table;
    private Boolean isDdl;
    private String type;
    private Long es;
    private Long ts;

    /**
     * 数据列表
     */
    private List<Map<String, Object>> data;

    public boolean getIsSave() {
        return !OperateType.DELETE.equals(type);
    }
}
