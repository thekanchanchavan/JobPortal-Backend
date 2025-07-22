package com.zidio.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "jobs")
public class JobsEntity {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY) //Auto -generate application
    @Column(name = "job_id",unique = true, nullable = false)
    private Integer jobId;
    private String jobTitle;
    private String jobDescription;
    private String companyName;
    private String companyWebsite;
    private String location;
    private String salaryRange;
    private String jobType;
    private String experienceRequired;
    private String skillsRequired;
    private String datePosted;
    private String applicationDeadline;
    private String recruiterUsername;
    private String educationRequired;
    public Integer getJobId() {
        return jobId;
    }
    public void setJobId(Integer jobId) {
        this.jobId = jobId;
    }
    public String getJobTitle() {
        return jobTitle;
    }
    public void setJobTitle(String jobTitle) {
        this.jobTitle = jobTitle;
    }
    public String getJobDescription() {
        return jobDescription;
    }
    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }
    public String getCompanyName() {
        return companyName;
    }
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
    public String getCompanyWebsite() {
        return companyWebsite;
    }
    public void setCompanyWebsite(String companyWebsite) {
        this.companyWebsite = companyWebsite;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getSalaryRange() {
        return salaryRange;
    }
    public void setSalaryRange(String salaryRange) {
        this.salaryRange = salaryRange;
    }
    public String getJobType() {
        return jobType;
    }
    public void setJobType(String jobType) {
        this.jobType = jobType;
    }
    public String getExperienceRequired() {
        return experienceRequired;
    }
    public void setExperienceRequired(String experienceRequired) {
        this.experienceRequired = experienceRequired;
    }
    public String getSkillsRequired() {
        return skillsRequired;
    }
    public void setSkillsRequired(String skillsRequired) {
        this.skillsRequired = skillsRequired;
    }
    public String getDatePosted() {
        return datePosted;
    }
    public void setDatePosted(String datePosted) {
        this.datePosted = datePosted;
    }
    public String getApplicationDeadline() {
        return applicationDeadline;
    }
    public void setApplicationDeadline(String applicationDeadline) {
        this.applicationDeadline = applicationDeadline;
    }
    public String getRecruiterUsername() {
        return recruiterUsername;
    }
    public void setRecruiterUsername(String recruiterUsername) {
        this.recruiterUsername = recruiterUsername;
    }
    public String getEducationRequired() {
        return educationRequired;
    }
    public void setEducationRequired(String educationRequired) {
        this.educationRequired = educationRequired;
    }
       

}
