package com.zidio.project.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.env.Environment;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.project.model.Internship;
import com.zidio.project.model.Jobs;
import com.zidio.project.model.Recruiter;
import com.zidio.project.service.RecruiterServiceImpl;
import com.zidio.project.service.StudentServiceImpl;

@RestController
@RequestMapping("/recruiter")
public class RecruiterAPI {
@org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
public org.springframework.http.ResponseEntity<ApiResponse> handleHttpMessageNotReadableException
(org.springframework.http.converter.HttpMessageNotReadableException ex){
    return new ResponseEntity<>(new ApiResponse(400,
    "Invalid JSON In Request Body"),HttpStatus.BAD_REQUEST);

}
@Autowired
private RecruiterServiceImpl recruiterService;
@Autowired
private StudentServiceImpl studentService;
@Autowired
public Environment environment;

@GetMapping("/myprofile/{username}")
public ResponseEntity<Recruiter> getMyProfile(@PathVariable("username")String username )throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
        ResponseEntity<Recruiter> response = recruiterService.getMyProfile(decodedUsername);
        return response;
    }catch(Exception exception){
        return new ResponseEntity<>(new Recruiter(),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping("/updateprofile")
public ResponseEntity<ApiResponse>updateProfile(@RequestBody Recruiter recruiter )throws Exception{
    try{
        System.out.println("updateProfile:"+recruiter.getUsername());
        ResponseEntity<ApiResponse> response = recruiterService.updateProfile(recruiter);
        return response;
    }catch(Exception exception){
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal server Error"),HttpStatus.INTERNAL_SERVER_ERROR);  
    }
}
@PostMapping("/addjob")
public ResponseEntity<ApiResponse> addJob(@RequestBody Jobs job) throws Exception{
    try{
        ResponseEntity<ApiResponse> response = recruiterService.addJob(job);
        return response;
    }catch(Exception exception){
        return new ResponseEntity<>(new ApiResponse(501,
        "Internak Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PutMapping("/updatejob")
public ResponseEntity<ApiResponse> updateJob(@RequestBody Jobs job) throws Exception{
    try{
        System.out.println("recruiter:"+job.getRecruiterUsername());
        ResponseEntity<ApiResponse> response = recruiterService.updateJob(job);
        return response;
    }catch(Exception exception){
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping("/addinternship")
public ResponseEntity<ApiResponse> addInternship(@RequestBody Internship internship)throws Exception{
    try{
        ResponseEntity<ApiResponse> response = recruiterService.addInternship(internship);
        return response;
    }catch(Exception exception){
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PutMapping("/updateinternship")
public ResponseEntity<ApiResponse> updateJob(@RequestBody Internship internship)throws Exception{
    try{
    ResponseEntity<ApiResponse> response = recruiterService.updateInternship(internship);
    return response;
    }catch(Exception exception){
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@DeleteMapping("/deletejob/{jobId}/{username}")
public ResponseEntity<ApiResponse> deleteJob(@PathVariable("jobId") Integer jobId,
@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
        ResponseEntity<ApiResponse> response = recruiterService.deleteJob(jobId,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in deleteJob:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@DeleteMapping("/deleteinternship/{internshipid}/{username}")
public ResponseEntity<ApiResponse> deleteInternship(@PathVariable("internshipid") Integer internshipid,
@PathVariable("username")String username)throws Exception{
    try{
        System.out.println("delete:"+username+internshipid);
        String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
        ResponseEntity<ApiResponse> response = recruiterService.deleteInternship(internshipid,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in deleteInternship:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@GetMapping("/alljobs")
public ResponseEntity<ApiResponseList> getAllJobs() throws Exception{
try{
    ResponseEntity<ApiResponseList> response = studentService.getAllJobs();
    return response;
}catch(Exception exception){
    System.out.println(("Exception in getAllJobs:"+ exception.getMessage()));
    List<Map<String, String>>noJobs = new ArrayList<>();
    noJobs.add(new HashMap<>());
    return new ResponseEntity<>(new ApiResponseList(501,noJobs),HttpStatus.INTERNAL_SERVER_ERROR);
}
}
@GetMapping("/allinternships")
public ResponseEntity<ApiResponseList> getAllInternships() throws Exception{
    try{
        ResponseEntity<ApiResponseList> resoponse = studentService.getAllInternships();
        return resoponse;
    }catch(Exception exception){
        System.out.println("Exception in getAllInternships:"+ exception.getMessage());
        List<Map<String, String>>noInternships = new ArrayList<>();
        noInternships.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(501,noInternships),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
@GetMapping("/jobsbyme/{username}")
public ResponseEntity<ApiResponseList> getAllJobsPostByMe(@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
        ResponseEntity<ApiResponseList> resoponse = recruiterService.getAllJobsPostedByMe(decodedUsername);
        return resoponse;
    }catch(Exception exception){
        System.out.println("Excception in getAllJobsPostedByMe" + exception.getMessage());
        List<Map<String,String>> noJobs = new ArrayList<>();
        return new ResponseEntity<>(new ApiResponseList(501,noJobs),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
@GetMapping("/internshipsbyme/{username}")
public ResponseEntity<ApiResponseList> getAllIntershipsPostedByMe(@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
        ResponseEntity<ApiResponseList> resoponse = recruiterService.getAllInternshipsPostedByMe(decodedUsername);
        return resoponse;
    }catch(Exception exception){
        System.out.println("Exception in getAllInternshipsPostedByme:"+exception.getMessage());
        List<Map<String, String>> noInternships = new ArrayList<>();
        noInternships.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(501,noInternships),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@GetMapping("/findjobbyid/{jobId}")
public ResponseEntity<ApiResponseList> findById(@PathVariable("jobId")Integer jobId)throws Exception{
    try{
        ResponseEntity<ApiResponseList> resoponse = studentService.findJobById(jobId);
        return resoponse;
    }catch(Exception exception){
        System.out.println("Exception in findById:"+exception.getMessage());
        List<Map<String,String>> noJobs = new ArrayList<>();
        noJobs.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(501,noJobs),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@GetMapping("/findinternshipbyid/{internshipId}")
public ResponseEntity<ApiResponseList> findInternshipByID(@PathVariable("internshipId")Integer internshipId)throws Exception{
  try{
    ResponseEntity<ApiResponseList> response = studentService.findInternshipById(internshipId);
    return response;
  }catch(Exception exception){
 System.out.println("Exception in findInternshipById:"+exception.getMessage());
 List<Map<String,String>> noInternships = new ArrayList<>();
 noInternships.add(new HashMap<>());
 return new ResponseEntity<>(new ApiResponseList(501, noInternships),HttpStatus.INTERNAL_SERVER_ERROR);
  }
}
@PostMapping("/applicationsforjob")
public ResponseEntity<ApiResponseList> getApplicationForJob(@RequestBody Jobs job)throws Exception{
    try{
        ResponseEntity<ApiResponseList>response = recruiterService.getJobApplicationForMyJobId(job);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in getApplicationForJob:"+exception.getMessage());
        List<Map<String,String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(501, noApplications),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping("/applicationsforinternship")
public ResponseEntity<ApiResponseList> getApplicationForInterbship(@RequestBody Internship internship)throws Exception{
    try{
        ResponseEntity<ApiResponseList>response = recruiterService.getInternshipApplicationForMyInternshipId(internship);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in GetApplicationForInternship:"+exception.getMessage());
        List<Map<String,String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(501, noApplications),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@PostMapping("/viewjobapplication/{applicationId}")
public ResponseEntity<ApiResponseList>viewjobapplication(@PathVariable("applicationId")Integer applicationId,
@RequestBody Recruiter recruiter) throws Exception{
    try{
    ResponseEntity<ApiResponseList> response = recruiterService.viewJobApplication(applicationId,recruiter.getUsername());
    return response;
    }catch(Exception exception){
        System.out.println("Exceptin in viewJobApplication:"+exception.getMessage());
        List<Map<String,String>>noApplications = new ArrayList<>();
noApplications.add(new HashMap<>());
return new ResponseEntity<>(new ApiResponseList(501, noApplications),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
@PostMapping("/viewinternshipapplication/{applicationId}")
public ResponseEntity<ApiResponseList> viewinternshipapplication(@PathVariable("applicationId")Integer applicationId,
@RequestBody Recruiter recruiter) throws Exception{
    try{
        ResponseEntity<ApiResponseList> resoponse = recruiterService.viewInternshipApplication(applicationId,recruiter.getUsername());
        return resoponse;
    }catch(Exception exception){
        System.out.println("Exception in ViewInternshipApplication:"+exception.getMessage());
        List<Map<String,String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(501, noApplications),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}@PostMapping("/selectjobapplication/{applicationId}")
public ResponseEntity<ApiResponse> selectJobApplication(@PathVariable("applicationId")Integer applicationId,
@RequestBody Recruiter recruiter) throws Exception{
    try{
        ResponseEntity<ApiResponse> resoponse = recruiterService.selectJobApplication(applicationId,recruiter.getUsername());
        return resoponse;
    }catch(Exception exception){
        System.out.println("Exception in selectionJobAllication:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
@PostMapping("/selectinternshipapplication/{applicationId}")
public ResponseEntity<ApiResponse> selectInternshipApplication(@PathVariable("applicationId")Integer applicationId,
@RequestBody Recruiter recruiter) throws Exception {
    try{
        ResponseEntity<ApiResponse>response = recruiterService.selectInternshipApplication(applicationId,recruiter.getUsername());
        return response;
    }catch(Exception exception){
        System.out.println("Exception in selecting Application:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}

@PostMapping("/rejectinternshipapplication/{applicationId}")
public ResponseEntity<ApiResponse> rejectInternshipApplication(@PathVariable("applicationId")Integer applicationId,
@RequestBody Recruiter recruiter) throws Exception {
    try{
        ResponseEntity<ApiResponse>response = recruiterService.rejectInternshipApplication(applicationId,recruiter.getUsername());
        return response;
    }catch(Exception exception){
        System.out.println("Exception in rejection Application:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
@PostMapping("/rejectjobapplication/{applicationId}")
public ResponseEntity<ApiResponse> rejectJobApplication(@PathVariable("applicationId")Integer applicationId,
@RequestBody Recruiter recruiter) throws Exception {
    try{
        ResponseEntity<ApiResponse>response = recruiterService.rejectJobApplication(applicationId,recruiter.getUsername());
        return response;
    }catch(Exception exception){
        System.out.println("Exception in rejection Application:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
@DeleteMapping("/deletejobapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse> deleteJobApplicationByRecruiter(@PathVariable("applicationId")Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
        ResponseEntity<ApiResponse>response = studentService.deleteJobApplicationByRecruiter(applicationId,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in deleteJobApplication:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Service Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@DeleteMapping("/deleteinternshipapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse> deleteApplicationByRecruiter(@PathVariable("applicationId")Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
        ResponseEntity<ApiResponse>response = studentService.deleteInternshipApplicationByRecruiter(applicationId,decodedUsername);
        return response;
    }catch(Exception exception){
    System.out.println("Exception in deleteInternshipApplication:"+ exception.getMessage());
    return new ResponseEntity<>(new ApiResponse(501,
    "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);    
    
    }
}

    
}
