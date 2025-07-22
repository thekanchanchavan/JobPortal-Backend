package com.zidio.project.model;

public class InternShipApplications {
    private Integer applicationid;
    private String appliedby;
    private Integer internshipid;
    private String status;
    private String appliedtime;
    public Integer getApplicationid(){
        return applicationid;
    }
    public void setApplicationid(Integer applicationid){
        this.applicationid=applicationid;
    }
    public String getAppliedby(){
        return appliedby;
    }
    public void setAppliedby(String appliedby){
        this.appliedby = appliedby;
    }
    public Integer getInternshipid(){
        return internshipid;
    }
    public void setInternshipid(Integer internshipid){
        this.internshipid = internshipid;
    }
    public  String getstatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getAppliedtime(){
        return appliedtime;
    }
    public void setAppliedtime(String appliedtime){
        this.appliedtime= appliedtime;
    }
    

}
