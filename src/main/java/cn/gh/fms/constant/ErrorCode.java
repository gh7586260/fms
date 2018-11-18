package cn.gh.fms.constant;

public enum ErrorCode {

    SYSTEM_ERROR(100, "系统异常"),
    USER_NOT_EXIST(101, "用户不存在"),
    PARAM_ERROR(102, "参数错误"),
    VALUE_ILLEGAL(103, "放你的狗屁，你花了这么多钱！？");

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
