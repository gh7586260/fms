package cn.gh.fms.controller;

import cn.gh.fms.BO.User;
import cn.gh.fms.constant.ErrorCode;
import cn.gh.fms.model.UserModel;
import cn.gh.fms.param.UpdatePhotoParam;
import cn.gh.fms.result.ListResult;
import cn.gh.fms.result.Result;
import cn.gh.fms.result.ResultUtils;
import cn.gh.fms.server.UserService;
import cn.gh.fms.trans.web.UserConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class UserController extends BaseController {

    @Autowired
    private UserService userService;
    @Autowired
    private UserConverter userConverter;

    //打开用户登录页面
    @RequestMapping(value = "/open/user/login", method = RequestMethod.GET)
    public String toLogin() {
        Long curUserId = super.getLoginUser();
        if (curUserId != null) {
            Result<User> userResult = this.userService.getById(curUserId);
            if (userResult.isSuccess()) {
                return "redirect:/to/user/info";
            }
            //cookie中的用户不存在，清楚当前cookie，重新登录
            super.updateUser(null);
        }
        return "user/login";
    }

    //打开用户信息页面
    @RequestMapping(value = "/to/user/info", method = RequestMethod.GET)
    public String toUserInfo() {
        return "user/userInfo";
    }

    //获得当前用户信息
    @RequestMapping(value = "/get/curUser", method = RequestMethod.GET)
    @ResponseBody
    public Result<UserModel> getCurUser() {
        Long curUserId = super.getLoginUser();
        if (curUserId == null) {
            return ResultUtils.error(ErrorCode.USER_NOT_EXIST);
        }
        Result<User> userResult = this.userService.getById(curUserId);
        if (!userResult.isSuccess()) {
            return ResultUtils.error(userResult.getCode(), userResult.getErrorMsg());
        }
        return ResultUtils.success(this.userConverter.convertUserModel(userResult.getResult()));
    }

    /**
     * 执行更改用户头像
     *
     * @param param
     * @param model
     * @return
     */
    @RequestMapping(value = "/do/user/modify/photo", method = RequestMethod.POST)
    public String doModifyPhoto(UpdatePhotoParam param, Model model) {
        if (param.getPhotoFile() == null) {
            model.addAttribute("errorMsg", "请选择更换的头像!");
            return "systemError";
        } else {
            Result<Void> saveImgResult = this.userService.doModifyPhoto(super.getLoginUser(), param.getPhotoFile());
            if (!saveImgResult.isSuccess()) {
                model.addAttribute("errorMsg", saveImgResult.getErrorMsg());
                return "systemError";
            }
            return "redirect:/to/user/info";
        }
    }


    //查询所有的用户列表
    @RequestMapping(value = "/query/all/users", method = RequestMethod.GET)
    @ResponseBody
    public ListResult<User> queryAllUsers() {
        ListResult<User> listResult = new ListResult<>();
        listResult.setList(this.userService.queryAllUsers());
        return listResult;
    }

    //用户检测
    @RequestMapping(value = "/user/check", method = RequestMethod.GET)
    @ResponseBody
    public Result<Void> checkUser(@RequestParam("userName") String userName, @RequestParam("password") String password) {
        Result<User> userResult = this.userService.getByUserNameAndPwd(userName, password);
        if (!userResult.isSuccess()) {
            return ResultUtils.error(userResult.getCode(), userResult.getErrorMsg());
        }
        return ResultUtils.success();
    }

    /**
     * 执行用户登录,登录后跳转用户信息页面
     *
     * @param userName
     * @param password
     * @param model
     * @return
     */
    @RequestMapping(value = "/user/login/excute", method = RequestMethod.GET)
    public String doLogin(@RequestParam("userName") String userName, @RequestParam("password") String password, Model model) {
        Result<User> userResult = this.userService.getByUserNameAndPwd(userName, password);
        if (!userResult.isSuccess()) {
            model.addAttribute("errorMsg", userResult.getErrorMsg());
            return "systemError";
        }
        //保存当前登录用户
        super.updateUser(userResult.getResult());
        return "user/userInfo";
    }

    //打开修改密码页面
    @RequestMapping(value = "/open/user/modify/pwd", method = RequestMethod.GET)
    public String openModifyPwd() {
        return "user/modifyPwd";
    }

    /**
     * 用户修改密码
     *
     * @param newPassword
     * @return
     */
    @RequestMapping(value = "/user/modify/password/excute", method = RequestMethod.PUT)
    @ResponseBody
    public Result<Void> modifyPwd(@RequestParam("newPassword") String newPassword) {
        if (StringUtils.isEmpty(newPassword)) {
            return ResultUtils.error(ErrorCode.PARAM_ERROR);
        }
        Long curUserId = super.getLoginUser();
        Result<Void> modifyResult = this.userService.updatePwd(curUserId, newPassword);
        if (!modifyResult.isSuccess()) {
            return ResultUtils.error(modifyResult.getCode(), modifyResult.getErrorMsg());
        }
        return ResultUtils.success();
    }

    /**
     * 用户登出
     *
     * @return
     */
    @RequestMapping(value = "/user/login/out", method = RequestMethod.GET)
    public String loginOut() {
        super.updateUser(null);
        return "user/login";
    }
}
