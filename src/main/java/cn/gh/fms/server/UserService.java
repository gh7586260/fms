package cn.gh.fms.server;

import cn.gh.fms.BO.User;
import cn.gh.fms.result.Result;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface UserService {

    /**
     * 根据ID获取用户信息
     *
     * @param userId
     * @return
     */
    Result<User> getById(long userId);

    /**
     * 执行用户修改头像
     *
     * @param userId
     * @param multipartFile
     * @return
     */
    Result<Void> doModifyPhoto(long userId, MultipartFile multipartFile);

    /**
     * 查询所有用户
     *
     * @return
     */
    List<User> queryAllUsers();

    /**
     * 通过用户名和密码获取用户信息
     *
     * @param userName
     * @param pwd
     * @return
     */
    Result<User> getByUserNameAndPwd(String userName, String pwd);

    /**
     * 用户修改密码
     *
     * @param newPwd
     * @return
     */
    Result<Void> updatePwd(Long userId, String newPwd);
}
