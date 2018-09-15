package com.yff.maosha.utils;

/**
 * command日志状态
 */
public enum CommandLogStatus {

    EMIT(0,"发射"),
    FAIL(1,"失败"),
    SUCCESS(2,"成功"),
    OPTM(3,"更新");

    private int code;
    private String desc;

    private CommandLogStatus(int code,String desc) {
        this.code = code;
        this.desc = desc;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }
}
