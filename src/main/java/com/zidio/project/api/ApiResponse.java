package com.zidio.project.api;
public class ApiResponse {

    private int status;
    private String messeage;
    public ApiResponse(int status, String messeage)
    {
        this.status = status;
        this.messeage = messeage;

    }
    public int getStatus(){
    return status;
    }
    public void setStatus(int status){
        this.status = status;
    }
    public String getMesseage(){
        return messeage;
    }
    public void setMesseage(String messeage){
        this.messeage = messeage;
    }
}
