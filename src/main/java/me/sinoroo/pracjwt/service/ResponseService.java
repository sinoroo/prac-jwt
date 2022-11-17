package me.sinoroo.pracjwt.service;

import java.util.List;
import org.springframework.stereotype.Service;

import me.sinoroo.pracjwt.model.response.CommonResult;
import me.sinoroo.pracjwt.model.response.ListResult;
import me.sinoroo.pracjwt.model.response.SingleResult;


@Service
public class ResponseService {
    public <T> SingleResult<T> getSingleResult(T data){
        SingleResult<T> result = new SingleResult<>();
        result.setData(data);
        setSuccessResult(result);
        return result;
    }

    public <T> ListResult<T> getListResult(List<T> list){
        ListResult<T> result = new ListResult<>();
        result.setData(list);
        setSuccessResult(result);
        return result;        
    }


    public CommonResult getSuccessResult() {
        CommonResult result = new CommonResult();
        setSuccessResult(result);
        return result;
    }

    public CommonResult getFailResult(int code, String msg) {
        CommonResult result = new CommonResult();
        result.setSuccess(false);
        setFailResult(result, code, msg);
        return result;
    }

    private void setFailResult(CommonResult result, int code, String msg){
        result.setSuccess(false);
        result.setCode(code);
        result.setMsg(msg);
    }


    private void setSuccessResult(CommonResult result) {
        result.setSuccess(true);
        result.setCode(CommonResponse.SUCCESS.getCode());
        result.setMsg(CommonResponse.SUCCESS.getMsg());
    }
}
