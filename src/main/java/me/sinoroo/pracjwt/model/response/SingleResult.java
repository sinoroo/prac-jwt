package me.sinoroo.pracjwt.model.response;

public class SingleResult<T> extends CommonResult {
    private T data;

    public T getData() {
        return this.data;
    }

    public void setData(T data) {
        this.data = data;
    }

}
