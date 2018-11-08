package cn.gh.fms.result;

import cn.gh.fms.constant.ErrorCode;

public class ResultUtils {

    public final static Result error(ErrorCode errorCode) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(errorCode.getCode());
        result.setErrorMsg(errorCode.getErrorMsg());
        return result;
    }

    public final static Result error(int code, String errorMsg) {
        Result result = new Result();
        result.setSuccess(false);
        result.setCode(code);
        result.setErrorMsg(errorMsg);
        return result;
    }

    public final static Result success() {
        Result result = new Result();
        result.setSuccess(true);
        return result;
    }

    public final static Result success(Object object) {
        Result result = new Result();
        result.setSuccess(true);
        result.setResult(object);
        return result;
    }
}
