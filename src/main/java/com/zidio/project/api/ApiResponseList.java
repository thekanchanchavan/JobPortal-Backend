package com.zidio.project.api;

import java.util.List;
import java.util.Map;

public class ApiResponseList {
    private int status;
    private List<Map<String, String>> data;
    public ApiResponseList(int status,List<Map<String,String>> data){
        this.status = status;
        this.data = data;
    }
    public int getStatus(){
        return status;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public List<Map<String, String>> getData(){
        return data;
    }
    public void  setData(List<Map<String,String>> data){
        this.data = data;
    }
    
}
