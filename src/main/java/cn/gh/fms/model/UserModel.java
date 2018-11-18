package cn.gh.fms.model;

/**
 * @author 郭宏
 * @date on 2018-11-18.
 */
public class UserModel {

    //用户ID
    private long userId;
    //用户名
    private String userName;
    //头像
    private String photo;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPhoto() {
        return photo;
    }

    public void setPhoto(String photo) {
        this.photo = photo;
    }
}
