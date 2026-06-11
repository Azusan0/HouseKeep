package com.cskaoyan.duolai.clean.mvc.advice;

import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;
import com.cskaoyan.duolai.clean.common.utils.Base64Utils;
import com.cskaoyan.duolai.clean.mvc.constants.HeaderConstants;
import com.cskaoyan.duolai.clean.mvc.model.Result;
import com.cskaoyan.duolai.clean.mvc.utils.RequestUtils;
import com.cskaoyan.duolai.clean.mvc.utils.ResponseUtils;
import com.cskaoyan.duolai.clean.common.expcetions.CommonException;
import feign.FeignException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class CommonExceptionHandler {


    /**
     * 捕获feign异常
     * @param e
     * @return
     */
    @ExceptionHandler({FeignException.class})
    public Result feignException(FeignException e) {
        throw new CommonException(ErrorInfo.Code.FEIGN_CLIENT_ERROR, ErrorInfo.Msg.REQUEST_FAILD);
    }

    /**
     * 自定义异常处理
     * @param e
     * @return
     */
    @ExceptionHandler({CommonException.class})
    public Result customException(CommonException e) {
        log.error("请求异常，message:{},e", e.getMessage(),e);
        // 标识异常已被处理
        ResponseUtils.setResponseHeader(HeaderConstants.BODY_PROCESSED, "1");
        if(RequestUtils.getRequest().getRequestURL().toString().contains("/inner/")) {
            CommonException commonException = new CommonException(e.getCode(), e.getMessage());
            ResponseUtils.setResponseHeader(HeaderConstants.INNER_ERROR, Base64Utils.encodeStr(e.getCode() + "|" + e.getMessage()));
            throw commonException;
        }
        return Result.error(e.getCode(), e.getMessage());
    }

    /**
     * 非自定义异常处理
     * @param e 异常
     * @return
     */
    @ExceptionHandler({Exception.class})
    public Result noCustomException(Exception e) {
        log.error("请求异常，", e);
        // 标识异常已被处理
        ResponseUtils.setResponseHeader(HeaderConstants.BODY_PROCESSED, "1");
        if(RequestUtils.getRequest().getRequestURL().toString().contains("/inner/")) {
            CommonException commonException = new CommonException(ErrorInfo.Msg.REQUEST_FAILD);

            ResponseUtils.setResponseHeader(HeaderConstants.INNER_ERROR, Base64Utils.encodeStr( "500|" + ErrorInfo.Msg.REQUEST_FAILD));
            throw commonException;
        }
        return Result.error(ErrorInfo.Msg.REQUEST_FAILD);
    }



}
