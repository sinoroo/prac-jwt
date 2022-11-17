package me.sinoroo.pracjwt.service;


public enum CommonResponse {
    
    SUCCESS( 0, "성공하였습니다."),
    FAIL( -1, "실패하였습니다.");

    private int code;
    private String msg;

    CommonResponse(int code, String msg){
        this.code = code;
        this.msg = msg;
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
