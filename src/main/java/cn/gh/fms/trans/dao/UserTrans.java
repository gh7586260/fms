package cn.gh.fms.trans.dao;

import cn.gh.fms.BO.User;
import cn.gh.fms.DO.UserDO;

import java.util.List;
import java.util.stream.Collectors;

public class UserTrans {

    public static User toBO(UserDO userDO) {
        User user = new User();
        user.setUserId(userDO.getUserId());
        user.setUserName(userDO.getUserName());
        user.setPassword(userDO.getPassword());
        user.setPhoto(userDO.getPhoto());
        user.setGmtCreated(userDO.getGmtCreated());
        user.setGmtLastModified(userDO.getGmtLastModified());
        return user;
    }

    public static List<User> toBOs(List<UserDO> userDOS) {
        if (userDOS == null) {
            return null;
        }
        return userDOS.stream().map(userDO -> toBO(userDO)).collect(Collectors.toList());
    }

    public static UserDO toDO(User user) {
        UserDO userDO = new UserDO();
        userDO.setUserId(user.getUserId());
        userDO.setUserName(user.getUserName());
        userDO.setPassword(user.getPassword());
        userDO.setPhoto(user.getPhoto());
        userDO.setGmtCreated(user.getGmtCreated());
        userDO.setGmtLastModified(user.getGmtLastModified());
        return userDO;
    }
}
