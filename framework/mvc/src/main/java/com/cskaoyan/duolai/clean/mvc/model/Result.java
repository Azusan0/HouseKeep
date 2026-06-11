package com.cskaoyan.duolai.clean.mvc.model;

import cn.hutool.http.HttpStatus;
import com.cskaoyan.duolai.clean.common.constants.HeaderConstants;
import com.cskaoyan.duolai.clean.mvc.utils.RequestUtils;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class Result <T> {

    public static final String REQUEST_OK = "OK";

    @ApiModelProperty(value = "业务状态码，200-成功，其它-失败")
    private int code;
    @ApiModelProperty(value = "响应消息", example = "OK")
    private String msg;
    @ApiModelProperty(value = "响应数据")
    private T data;

    public static Result<Void> ok() {
        return new Result<Void>(HttpStatus.HTTP_OK, REQUEST_OK, null);
    }

    public static <T> Result<T> ok(T data) {
        return new Result<>(HttpStatus.HTTP_OK, REQUEST_OK, data);
    }

    public static <T> Result<T> error(String msg) {
        return new Result<>(HttpStatus.HTTP_BAD_REQUEST, msg, null);
    }

    public static <T> Result<T> error(int code, String msg) {
        return new Result<>(code, msg, null);
    }

    public Result() {
    }

    public Result(int code, String msg, T data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
    }

    public boolean success() {
        return code == HttpStatus.HTTP_OK;
    }
}
