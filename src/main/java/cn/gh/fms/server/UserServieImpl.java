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
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.List;

@Service
public class UserServieImpl implements UserService {

    private final static Logger LOG = LoggerFactory.getLogger(UserServieImpl.class);

    @Autowired
    private UserMapper userMapper;
    @Value("${local.file.base.dir}")
    private String localFileDir;

    @Override
    public Result<User> getById(long userId) {
        UserDO userDO = this.userMapper.getById(userId);
        if (userDO == null) {
            return ResultUtils.error(ErrorCode.USER_NOT_EXIST);
        }
        return ResultUtils.success(UserTrans.toBO(userDO));
    }

    @Override
    public Result<Void> doModifyPhoto(long userId, MultipartFile multipartFile) {
        File fileDir = new File(localFileDir);
        File oldFile = null;
        File targetFile = new File(localFileDir.concat("/user" + userId + "_").concat(System.currentTimeMillis() + ".jpg"));
        String[] names = fileDir.list();
        for (String fileName : names) {
            if (fileName.startsWith("user" + userId + "_")) {
                oldFile = new File(fileDir.getAbsolutePath().concat("/") + fileName);
                break;
            }
        }
        if (oldFile != null && oldFile.exists()) {
            oldFile.delete();
        }
        try {
            this.saveLocalImage(multipartFile.getInputStream(), targetFile);
        } catch (IOException e) {
            e.printStackTrace();
            LOG.error("Get file data failed", e);
            return ResultUtils.error(ErrorCode.SYSTEM_ERROR);
        }
        return ResultUtils.success();
    }

    //保存图片到本地
    private File saveLocalImage(InputStream inStream, File imageFile) {
        String imagePath = imageFile.getPath();
        File tempFile = new File(imagePath);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        try {
            //创建一个Buffer字符串
            byte[] buffer = new byte[1024];
            //每次读取的字符串长度，如果为-1，代表全部读取完毕
            int len = 0;
            //使用一个输入流从buffer里把数据读取出来
            while ((len = inStream.read(buffer)) != -1) {
                //用输出流往buffer里写入数据，中间参数代表从哪个位置开始读，len代表读取的长度
                outStream.write(buffer, 0, len);
            }
            //关闭输入流
            inStream.close();
            //把outStream里的数据写入内存
            //得到图片的二进制数据，以二进制封装得到数据，具有通用性
            byte[] data = outStream.toByteArray();
            //创建输出流
            FileOutputStream fileOutStream = new FileOutputStream(tempFile);
            //写入数据
            fileOutStream.write(data);
            fileOutStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            try {
                outStream.close();
            } catch (IOException e) {
            }
        }
        return tempFile;
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
