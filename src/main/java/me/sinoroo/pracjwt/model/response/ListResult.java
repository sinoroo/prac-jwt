package me.sinoroo.pracjwt.model.response;

import java.util.List;

public class ListResult<T> extends CommonResult {
    private List<T> data;
    //String data;
    
    public List<T> getData() {
        return this.data;
    }
    
    public void setData(List<T> data) {
        this.data = data;
    }
    
}
