package com.zidio.project.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.zidio.project.api.ApiResponseList;
import com.zidio.project.api.ApiResponse;
import com.zidio.project.dao.StudentDAOImpl;
import com.zidio.project.model.InternShipApplications;
import com.zidio.project.model.JobApplications;
import com.zidio.project.model.Student;

@Transactional
@Service(value ="StudentService")
public class StudentServiceImpl {
@Autowired
private StudentDAOImpl studentDAO;

public ResponseEntity<Student> getMyProfile(String username)throws Exception{
    if(username == null || username.isEmpty()){
        return new ResponseEntity<>(new Student(), HttpStatus.NOT_FOUND);
    }
    return studentDAO.getMyProfile(username);
}
public ResponseEntity<ApiResponse> updateProfile(Student student)throws Exception{
    String username = student.getUsername();
    if(username == null ||username.isEmpty()){
        return new ResponseEntity<>(new ApiResponse(400,
        "Username is required"), HttpStatus.BAD_REQUEST);
    }
    return studentDAO.updateProfile(student);
}
public ResponseEntity<ApiResponse> applyJob(JobApplications jobApplication)throws Exception{
    String username = jobApplication.getAppliedby();
    Integer jobId = jobApplication.getJobid();
    if(username == null || username.isEmpty()){
        return new ResponseEntity<>(new ApiResponse(400,
        "USername is required"), HttpStatus.BAD_REQUEST);
    }
    if(jobId == null || jobId <= 0){
        return new ResponseEntity<>(new ApiResponse(400,
        "Job Id is required"), HttpStatus.BAD_REQUEST);
    }
    return studentDAO.applyJob(jobId, username);
}
public ResponseEntity<ApiResponse> applyInternship(InternShipApplications internshipApplication)throws Exception{
    String username = internshipApplication.getAppliedby();
    Integer internshipId = internshipApplication.getInternshipid();
    if(internshipId == null || internshipId <= 0){
        return new ResponseEntity<>(new ApiResponse(400,
        "Internship Id is required"),HttpStatus.BAD_REQUEST);
    }
    if(username == null || username.isEmpty()){
        return new ResponseEntity<>(new ApiResponse(400,
        "USername is required"),HttpStatus.BAD_REQUEST);
    }
    return studentDAO.applyInternship(internshipId,username);
}
public ResponseEntity<ApiResponseList> getMyAppliedJobs(String username) throws Exception{
    if(username == null || username.isEmpty()){
        System.out.println("Username is required");
        List<Map<String ,String>> noJobs = new ArrayList<>();
        noJobs.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(400,noJobs),HttpStatus.NOT_FOUND);
    }
    return studentDAO.getMyAppliedJobs(username);
}
public ResponseEntity<ApiResponseList> getMyAppliedInternships(String username) throws Exception{
    if(username == null || username.isEmpty()){
        System.out.println("Username is required");
        List<Map<String ,String>> noInternships = new ArrayList<>();
        noInternships.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(400,noInternships),HttpStatus.NOT_FOUND);
    }
    return studentDAO.getMyAppliedInternships(username);
}
public ResponseEntity<ApiResponseList> getAllJobs() throws Exception{
    return studentDAO.getAllJobs();
}
public ResponseEntity<ApiResponseList> getAllInternships()throws Exception{
    return studentDAO.getAllInternships();
}
public ResponseEntity<ApiResponseList> findJobById(Integer jobId)throws Exception{
    if(jobId == null || jobId <= 0){
        List<Map<String,String>>noJobs = new ArrayList<>();
        noJobs.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404,noJobs),HttpStatus.NOT_FOUND);
    }
    return studentDAO.findJobById(jobId);
}
public ResponseEntity<ApiResponseList> findInternshipById(Integer internshipId)throws Exception{
    if(internshipId == null || internshipId <= 0){
        List<Map<String,String>> noInternships = new ArrayList<>();
        noInternships.add(new HashMap<>());
        return new  ResponseEntity<>(new ApiResponseList(404,noInternships),HttpStatus.NOT_FOUND);

    }
    return studentDAO.findInternshipById(internshipId);
}
public ResponseEntity<ApiResponseList> viewJobApplicationByStudent(Integer applicationId,String username)throws Exception{
    if(username == null || username.isEmpty() || applicationId == null || applicationId<=0){
        List<Map<String,String>> noInternships = new ArrayList<>();
        noInternships.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404,noInternships),HttpStatus.NOT_FOUND);

    }
    return studentDAO.viewJobApplicationByStudent(applicationId,username);
}
public ResponseEntity<ApiResponseList> viewInternshipApplicationByStudent(Integer applicationId, String username)throws Exception{
if(username == null || username.isEmpty() || applicationId == null || applicationId <=0){
    List<Map<String,String>> noInternships = new ArrayList<>();
    noInternships.add(new HashMap<>());
    return new ResponseEntity<>(new ApiResponseList(404,noInternships),HttpStatus.NOT_FOUND);

}
return studentDAO.viewInternshipApplicationByStudent(applicationId,username);
}
public ResponseEntity<ApiResponse> withdrawJobApplication(Integer applicationId ,String username)throws Exception{
    if(username == null || username.isEmpty() || applicationId == null || applicationId<=0){
        return new ResponseEntity<>(new ApiResponse(400,
        "USername and Applicationb ID are required"),HttpStatus.NOT_FOUND);
    }
    return studentDAO.withdrawJobApplication(applicationId, username);
}
public ResponseEntity<ApiResponse> withdrawInternshipApplication(Integer applicationId, String username)throws Exception{
    if(username == null || username.isEmpty() || applicationId == null || applicationId <= 0){
        return new ResponseEntity<>(new ApiResponse(400,
        "Username and Application Id are required"),HttpStatus.BAD_REQUEST);
    }
    return studentDAO.withdrawInternshipApplication(applicationId,username);
}
public ResponseEntity<ApiResponse> reapplyJobApplication(Integer applicationId,String username) throws Exception{
    if(username == null || username.isEmpty() || applicationId == null || applicationId<= 0){
        return new ResponseEntity<>(new ApiResponse(404,
        "USername and Application ID are Required"), HttpStatus.NOT_FOUND);
    }
    return studentDAO.reapplyJobApplication(applicationId,username);
}
public ResponseEntity<ApiResponse> reapplyInternshipApplication(Integer applicationId,String username) throws Exception{
    if(username == null || username.isEmpty() || applicationId == null || applicationId<= 0){
        return new ResponseEntity<>(new ApiResponse(404,
        "USername and Application ID are Required"), HttpStatus.NOT_FOUND);
    }
    return studentDAO.reapplyInternshipApplication(applicationId,username);
}
public ResponseEntity<ApiResponse> deleteJobApplication(Integer applicationId, String username)throws Exception{
    if(username == null || username.isEmpty() || applicationId == null || applicationId<= 0){
        return  new ResponseEntity<>(new ApiResponse(400,"Application and application Id are required"),HttpStatus.BAD_REQUEST);
    }
    return studentDAO.deleteJobApplication(applicationId,username);
}
public ResponseEntity<ApiResponse> deleteInternshipApplication(Integer applicationId,String username)throws Exception{
    if(username == null || username.isEmpty() || applicationId == null ||applicationId<=0){
        return new ResponseEntity<>(new ApiResponse(400,
        "USername and Application ID are required"),HttpStatus.BAD_REQUEST);
    }
    return studentDAO.deleteInternshipApplication(applicationId,username);
}
public ResponseEntity<ApiResponse> deleteInternshipApplicationByRecruiter(Integer applicationId, String username)throws Exception{
    if(username == null || username.isEmpty()|| applicationId == null || applicationId<= 0){
        return new ResponseEntity<>(new ApiResponse(400,
        "USername and Application Id are required"),HttpStatus.BAD_REQUEST);
    }
    return studentDAO.deleteInternshipApplicationByRecruiter(applicationId, username);
}
public ResponseEntity<ApiResponse> deleteJobApplicationByRecruiter(Integer applicationId,String username)throws Exception{
    if(username == null || username.isEmpty() || applicationId == null || applicationId <= 0){
        return new ResponseEntity<>(new ApiResponse(400,
        "Username and Application ID are required" ),HttpStatus.BAD_REQUEST);
    }
    return studentDAO.deleteJobApplicationByRecruiter(applicationId,username);
 }
}
