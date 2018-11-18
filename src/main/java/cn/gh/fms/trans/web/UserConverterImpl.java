package cn.gh.fms.trans.web;

import cn.gh.fms.BO.User;
import cn.gh.fms.model.UserModel;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author 郭宏
 * @date on 2018-11-18.
 */
@Component
public class UserConverterImpl implements UserConverter {

    @Value("${fms.page.file.path}")
    private String filePath;

    @Override
    public UserModel convertUserModel(User user) {
        UserModel userModel = new UserModel();
        userModel.setUserId(user.getUserId());
        userModel.setUserName(user.getUserName());
        userModel.setPhoto(this.filePath.concat(user.getPhoto()));
        return userModel;
    }
}
