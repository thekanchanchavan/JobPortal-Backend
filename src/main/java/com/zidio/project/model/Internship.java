package com.zidio.project.model;

public class Internship {
    private Integer internshipId;
    private String companyName;
    private String internshipTitle;
    private String internshipDescription;
    private String startDate;
    private String endDate;
    private String location;
    private String stipend;
    private String skillsRequired;
    private String educationRequired;
    private String  datePosted;
    private String  applicationDeadline;
    private String recruterUsername;
    private String companyWebsite;
    public String getLocation(){
        return location;
    }
    public void setLocation(String location){
        this.location = location;
    }
    public String getStipend(){
        return stipend;
    }
    public void setStipend(String stipend){
        this.stipend = stipend;
    }
    public String getSkillsRequired(){
        return skillsRequired;
    }
    public void setSkillsRequired(String skillsRequried){
        this.skillsRequired = skillsRequried;
    }
    public String getEducationRequired(){
        return educationRequired;
    }
    public void setEducationRequired(String educationRequired){
        this.educationRequired = educationRequired;
    }
    public String getDatePosted(){
        return datePosted;
    }
    public void setDatePosted(String datePosted){
        this.datePosted = datePosted;
    }
    public String getApplicationDeadline(){
        return applicationDeadline;
    }
    public void setApplicationDeadline(String applicationDeadLine){
        this.applicationDeadline = applicationDeadLine;
    }
    public String getRecruiterUsername(){
        return recruterUsername ;
    }
    public void setRecruiterUsername(String recruiterUSername){
        this. recruterUsername = recruiterUSername;
    }
    public String getCompanyWebsite(){
        return companyWebsite;
    }
    public void setCompanyWebsite(String companyWebsite){
        this.companyWebsite = companyWebsite;
    }
    public Integer getInternshipId(){
        return internshipId;
    } 
    public void setInternshipId(Integer internshipId){
        this.internshipId = internshipId;
    }
    public String getCompanyName(){
        return companyName;
    }
    public void setCompanyName(String companyName){
        this.companyName= companyName;
    }
    public String getInternshipTitle(){
        return internshipTitle;
    }
    public void setInternshipTitle(String internshipTitle){
        this.internshipTitle = internshipTitle;
    }
    public String getInternshipDescription(){
        return internshipDescription;
    }
    public void setInternshipDescription(String internshipDescription){
        this.internshipDescription = internshipDescription;
    }
    public String getStartDate(){
        return startDate;
    }
    public void setStartDate(String startDate){
        this.startDate = startDate;
    }
    public String getEndDate(){
        return endDate;
    }
    public void setEndDate(String endDate){
        this.endDate = endDate;
    }



    
}
