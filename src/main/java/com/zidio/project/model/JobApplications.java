package com.zidio.project.model;

public class JobApplications {
    private Integer applicationid;
    private String appliedby;
    private Integer jobid;
    private String status;
    private String appliedtime;
    public Integer getApplicationid() {
        return applicationid;
    }
    public void setApplicationid(Integer applicationid) {
        this.applicationid = applicationid;
    }
    public String getAppliedby() {
        return appliedby;
    }
    public void setAppliedby(String appliedby) {
        this.appliedby = appliedby;
    }
    public Integer getJobid() {
        return jobid;
    }
    public void setJobid(Integer jobid) {
        this.jobid = jobid;
    }
    public String getStatus() {
        return status;
    }
    public void setStatus(String status) {
        this.status = status;
    }
    public String getAppliedtime() {
        return appliedtime;
    }
    public void setAppliedtime(String appliedtime) {
        this.appliedtime = appliedtime;
    }

}
