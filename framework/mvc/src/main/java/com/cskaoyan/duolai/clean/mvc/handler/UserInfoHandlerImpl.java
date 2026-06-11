package com.cskaoyan.duolai.clean.mvc.handler;

import com.cskaoyan.duolai.clean.common.model.CurrentUserInfo;
import com.cskaoyan.duolai.clean.mvc.utils.UserContext;
import com.cskaoyan.duolai.clean.common.handler.UserInfoHandler;
import org.springframework.stereotype.Component;

@Component
public class UserInfoHandlerImpl implements UserInfoHandler {
    @Override
    public CurrentUserInfo currentUserInfo() {
        return UserContext.currentUser();
    }
}
