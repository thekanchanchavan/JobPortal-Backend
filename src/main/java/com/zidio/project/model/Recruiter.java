package com.zidio.project.model;
public class Recruiter {
    private String username;
    private String name;
    private Long mobilenumber;
    private String location;
    private String companyname;
    public String getCompanyname() {
        return companyname;
    }
    public void setCompanyname(String companyname) {
        this.companyname = companyname;
    }
    private String image;
    public String getUsername() {
        return username;
    }
    public void setUsername(String username) {
        this.username = username;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public Long getMobilenumber() {
        return mobilenumber;
    }
    public void setMobilenumber(Long mobilenumber) {
        this.mobilenumber = mobilenumber;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public String getImage() {
        return image;
    }
    public void setImage(String image) {
        this.image = image;
    }
    
    
}
