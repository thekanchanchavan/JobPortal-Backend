package com.zidio.project.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "recruiters")
public class RecruiterEntity {
@Id
private String username;
private String name;
private String location;
private Long mobilenumber;
private String companyname;
@Column(length = 5000000)
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
public String getLocation() {
    return location;
}
public void setLocation(String location) {
    this.location = location;
}
public Long getMobilenumber() {
    return mobilenumber;
}
public void setMobilenumber(Long mobilenumber) {
    this.mobilenumber = mobilenumber;
}
public String getCompanyname() {
    return companyname;
}
public void setCompanyname(String companyname) {
    this.companyname = companyname;
}
public String getImage() {
    return image;
}
public void setImage(String image) {
    this.image = image;
}

    
}
