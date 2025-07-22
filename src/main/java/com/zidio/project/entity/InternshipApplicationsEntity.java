package com.zidio.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="internshipapplications")
public class InternshipApplicationsEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)//Auto-generate applications
    @Column(name ="applicationid",unique=true, nullable=false)
    private Integer applicationid;
    private String appliedby;
    private Integer internshipid;
    private String status;
    private String appliedtime;
    public Integer getApplicationid(){
       return applicationid; 
    }
    public void setApplicationid(Integer applicationid){
        this.applicationid = applicationid;
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
        this.internshipid= internshipid;
    }
    public String getStatus(){
        return status;
    }
    public void setStatus(String status){
        this.status = status;
    }
    public String getAppliedtime(){
        return appliedtime;
    }
    public void setAppliedtime(String appliedtime){
        this.appliedtime = appliedtime;
    }


    
}
