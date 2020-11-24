package com.jjs.zero.httplibrary.error;

/**
 * @Author: jiajunshuai
 * @CreateTime: 2020/11/24
 * @Details: <功能描述>
 */

import android.text.TextUtils;

public enum ErrorInfo {
    Bz("02-01", ""),
    Params("02-02", "参数错误"),
    Network("02-03", "网络错误，请检查网络是否正常连接"),
    NetworkTimeOut("02-04", "网络超时"),
    ServerError("02-05", "服务异常"),
    HttpError("02-08", ""),
    JsonError("02-09", "解析失败");

    public String code;
    public String msg;

    ErrorInfo(String code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public ErrorMsg getErrorMessage(String code, String msg) {
        if (code != null) {
            code = this.code + "-" + code;
        } else {
            code = this.code;
        }
        if (msg == null) {
            msg = this.msg;
        }
        return new ErrorMsg(code, msg);
    }

    public ErrorMsg getErrorMessage() {
        return new ErrorMsg(code, msg);
    }

    public boolean equals(String code) {
        if (TextUtils.isEmpty(code)) {
            return false;
        }

        return this.code.startsWith(code);
    }

    public boolean equals(ErrorMsg error) {
        if (error == null) {
            return false;
        }

        return equals(error.getErrorCode());
    }

}
