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

import com.zidio.project.api.ApiResponse;
import com.zidio.project.api.ApiResponseList;
import com.zidio.project.dao.RecruiterDAOImpl;
import com.zidio.project.dao.StudentDAOImpl;
import com.zidio.project.model.Jobs;
import com.zidio.project.model.Internship;
import com.zidio.project.model.Recruiter;
@Transactional
@Service(value="RecruiterService")
public class RecruiterServiceImpl {
    @Autowired
    private RecruiterDAOImpl recruiterDAO;
    @Autowired
    private StudentDAOImpl studentDAO;

    public ResponseEntity<Recruiter> getMyProfile(String username)throws Exception{
        if(username == null || username.isEmpty()){
            return new ResponseEntity<>(new Recruiter(),HttpStatus.BAD_REQUEST);
        }
        return recruiterDAO.getMyProfile(username);
    }
    public ResponseEntity<ApiResponse> updateProfile(Recruiter recruiter) throws Exception{
        String username = recruiter.getUsername();
        if(username == null || username.isEmpty()){
            return new ResponseEntity<>(new ApiResponse(400,
            "username is required"),HttpStatus.BAD_REQUEST);
        }
        return recruiterDAO.updateProfile(recruiter);
    }
    public ResponseEntity<ApiResponse> updateJob(Jobs job) throws Exception{
        if (job.getJobId() == null || job.getJobId()<= 0 || 
         job.getRecruiterUsername() == null || job.getRecruiterUsername().isEmpty()){
        return new ResponseEntity<>(new ApiResponse(400,
        "Job Id & username is required"),HttpStatus.BAD_REQUEST);
    }
    return recruiterDAO.updateJob(job);
    
    }
    public ResponseEntity<ApiResponse> addJob(Jobs job)throws Exception{
        return recruiterDAO.addJob(job);
    }
    public ResponseEntity<ApiResponse> addInternship(Internship internship)throws Exception{
        return recruiterDAO.addInternship(internship);
    }

public ResponseEntity<ApiResponse> updateInternship(Internship internship) throws Exception{
    if(internship.getInternshipId() == null || internship.getInternshipId()<=0 ||
       internship.getRecruiterUsername() == null || internship.getRecruiterUsername().isEmpty()){
        return new ResponseEntity<>(new ApiResponse(400,
        "Internship Id & username is required"), HttpStatus.BAD_REQUEST);
    }
    return recruiterDAO.updateInternship(internship);
}
public ResponseEntity<ApiResponse> deleteJob(Integer jobId, String username)throws Exception{
    if(jobId <= 0 || jobId == null ||
    username == null || username.isEmpty()){
        return new ResponseEntity<>(new ApiResponse(400,
        "Job Id & username is required"), HttpStatus.BAD_REQUEST);
    }
    return recruiterDAO.deleteJob(jobId,username);
}
public ResponseEntity<ApiResponse> deleteInternship(Integer internshipId, String username)throws Exception{
    if(internshipId <= 0 || internshipId == null ||
    username == null || username.isEmpty()){
        return new ResponseEntity<>(new ApiResponse(400,
        "Internship ID & username is required"),HttpStatus.BAD_REQUEST);
    }
    return recruiterDAO.deleteInternship(internshipId, username);
}
public ResponseEntity<ApiResponseList> getAllJobsPostedByMe(String username) throws Exception{
    if(username == null || username.isEmpty()){
        List<Map<String, String>> noJobs = new ArrayList<>();
        noJobs.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404,noJobs),HttpStatus.NOT_FOUND);
    }
    return studentDAO.getAllJobsPostedByMe(username);
}
public ResponseEntity<ApiResponseList>getAllInternshipsPostedByMe(String username) throws Exception{
    if(username == null || username.isEmpty()){
        List<Map<String,String>> noInternships = new ArrayList<>();
        noInternships.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404, noInternships),HttpStatus.NOT_FOUND);
    }
    return studentDAO.getAllInternshipsPostedByMe(username);
}
public ResponseEntity<ApiResponseList> getJobApplicationForMyJobId(Jobs job) throws Exception{
    if(job.getJobId() == null || job.getJobId() <= 0 ||
    job.getRecruiterUsername() == null || job.getRecruiterUsername().isEmpty()){
        List<Map<String, String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404,noApplications),HttpStatus.NOT_FOUND);
    }
    return studentDAO.getJobApplicationForMyJobId(job.getJobId(),job.getRecruiterUsername());
}
public ResponseEntity<ApiResponseList> getInternshipApplicationForMyInternshipId(Internship internship)throws Exception{
    if(internship.getInternshipId() == null || internship.getInternshipId()<=0 ||
    internship.getRecruiterUsername() == null ||internship.getRecruiterUsername().isEmpty()){
        List<Map<String,String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404,noApplications),HttpStatus.NOT_FOUND);
    }
    return studentDAO.getInternshipApplicationsForMyInternshipId(internship.getInternshipId(),internship.getRecruiterUsername());
}
public ResponseEntity<ApiResponseList> viewJobApplication(Integer applicationID , String recruiterUsername)throws Exception{
    if(applicationID<= 0 || applicationID == null || recruiterUsername == null || recruiterUsername.isEmpty()){
        List<Map<String,String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404, noApplications),HttpStatus.NOT_FOUND);
    }
    return studentDAO.viewJobApplication(applicationID,recruiterUsername);
}
public ResponseEntity<ApiResponseList> viewInternshipApplication(Integer applicationID , String recruiterUsername)throws Exception{
    if(applicationID<= 0 || applicationID == null || recruiterUsername == null || recruiterUsername.isEmpty()){
        List<Map<String,String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404, noApplications),HttpStatus.NOT_FOUND);
    }
    return studentDAO.viewInternshipApplication(applicationID,recruiterUsername);
}
public ResponseEntity<ApiResponse> selectJobApplication(Integer applicationId, String recruiterUsername)throws Exception{
    if(applicationId <= 0 || applicationId == null || recruiterUsername == null || recruiterUsername.isEmpty()){
    return new ResponseEntity<>(new ApiResponse(404,
    "Application Id & username is required"),HttpStatus.NOT_FOUND);
    }
    return studentDAO.selectJobApplication(applicationId, recruiterUsername);
}
public ResponseEntity<ApiResponse> selectInternshipApplication(Integer applicationId, String recruiterUsername)throws Exception{
    if(applicationId <= 0 || applicationId == null || recruiterUsername == null || recruiterUsername.isEmpty()){
    return new ResponseEntity<>(new ApiResponse(404,
    "Application Id & username is required"),HttpStatus.NOT_FOUND);
    }
    return studentDAO.selectInternshipApplication(applicationId, recruiterUsername);
}
public ResponseEntity<ApiResponse> rejectJobApplication(Integer applicationId, String recruiterUsername)throws Exception{
    if(applicationId <= 0 || applicationId == null || recruiterUsername == null || recruiterUsername.isEmpty()){
    return new ResponseEntity<>(new ApiResponse(404,
    "Application Id & username is required"),HttpStatus.NOT_FOUND);
    }
    return studentDAO.rejectJobApplication(applicationId, recruiterUsername);
}
public ResponseEntity<ApiResponse> rejectInternshipApplication(Integer applicationId, String recruiterUsername)throws Exception{
    if(applicationId <= 0 || applicationId == null || recruiterUsername == null || recruiterUsername.isEmpty()){
    return new ResponseEntity<>(new ApiResponse(404,
    "Application Id & username is required"),HttpStatus.NOT_FOUND);
    }
    return studentDAO.rejectInternshipApplication(applicationId, recruiterUsername);
}
}