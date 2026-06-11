package com.cskaoyan.duolai.clean.common.expcetions;


import com.cskaoyan.duolai.clean.common.constants.ErrorInfo;

import static java.net.HttpURLConnection.HTTP_SERVER_ERROR;

public class DBException extends CommonException {
    public DBException() {
        super(HTTP_SERVER_ERROR, ErrorInfo.Msg.PROCESS_FAILD);
    }

    public DBException( String message) {
        super(HTTP_SERVER_ERROR, message);
    }

    public DBException(Throwable throwable, String message) {
        super(throwable, HTTP_SERVER_ERROR, message);
    }

    public DBException(Throwable throwable) {
        super(throwable, HTTP_SERVER_ERROR, ErrorInfo.Msg.PROCESS_FAILD);
    }
}
