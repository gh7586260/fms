package cn.gh.fms.trans.web;

import cn.gh.fms.BO.User;
import cn.gh.fms.model.UserModel;

/**
 * @author 郭宏
 * @date on 2018-11-18.
 */
public interface UserConverter {

    UserModel convertUserModel(User user);
}
