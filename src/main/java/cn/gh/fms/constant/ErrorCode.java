package cn.gh.fms.constant;

public enum ErrorCode {

    USER_NOT_EXIST(101, "用户不存在"),
    PARAM_ERROR(102, "参数错误");

    //错误编码
    private int code;
    //错误信息
    private String errorMsg;

    ErrorCode(int code, String errorMsg) {
        this.code = code;
        this.errorMsg = errorMsg;
    }

    public int getCode() {
        return code;
    }

    public String getErrorMsg() {
        return errorMsg;
    }
}
