package cn.gh.fms.mapper;

import cn.gh.fms.DO.UserDO;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {

    /**
     * 根据ID获取用户
     *
     * @param userId
     * @return
     */
    UserDO getById(@Param("userId") long userId);

    List<UserDO> queryAllUsers();

    UserDO getByUserNameAndPwd(@Param("userName") String userName, @Param("password") String password);

    boolean updatePwd(@Param("userId") long userId, @Param("newPassword") String newPassword);

    boolean updatPhoto(@Param("userId") long userId, @Param("photo") String photo);
}
