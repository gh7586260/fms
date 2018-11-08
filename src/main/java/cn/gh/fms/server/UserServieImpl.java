package cn.gh.fms.server;

import cn.gh.fms.BO.User;
import cn.gh.fms.DO.UserDO;
import cn.gh.fms.constant.ErrorCode;
import cn.gh.fms.mapper.UserMapper;
import cn.gh.fms.result.Result;
import cn.gh.fms.result.ResultUtils;
import cn.gh.fms.trans.dao.UserTrans;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserServieImpl implements UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserServieImpl.class);

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<User> getById(long userId) {
        UserDO userDO = this.userMapper.getById(userId);
        if (userDO == null) {
            return ResultUtils.error(ErrorCode.USER_NOT_EXIST);
        }
        return ResultUtils.success(UserTrans.toBO(userDO));
    }

    @Override
    public List<User> queryAllUsers() {
        List<UserDO> userDOS = this.userMapper.queryAllUsers();
        return UserTrans.toBOs(userDOS);
    }

    @Override
    public Result<User> getByUserNameAndPwd(String userName, String pwd) {
        UserDO userDO = this.userMapper.getByUserNameAndPwd(userName, pwd);
        if (userDO == null) {
            return ResultUtils.error(ErrorCode.USER_NOT_EXIST);
        }
        return ResultUtils.success(UserTrans.toBO(userDO));
    }

    @Override
    @Transactional
    public Result<Void> updatePwd(Long userId, String newPwd) {
        if (userId == null) {
            return ResultUtils.error(ErrorCode.USER_NOT_EXIST);
        }
        //更新用户密码
        this.userMapper.updatePwd(userId, newPwd);
        return ResultUtils.success();
    }

}
