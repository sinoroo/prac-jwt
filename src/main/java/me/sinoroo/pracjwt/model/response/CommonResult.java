package me.sinoroo.pracjwt.model.response;

public class CommonResult {

    //@ApiModelProperty(value = "응답 성공 여부: T/F")
    private boolean success;

    //@ApiModelProperty(value = "응답 코드: >= 0 정상, < 0 비정상")
    private int code;

    //@ApiModelProperty(value = "응답 메시지")
    private String msg;

    public boolean isSuccess() {
        return this.success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public int getCode() {
        return this.code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
    
}
