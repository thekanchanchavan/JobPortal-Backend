package com.zidio.project.api;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.zidio.project.model.InternShipApplications;
import com.zidio.project.model.JobApplications;
import com.zidio.project.model.Student;
import com.zidio.project.service.StudentServiceImpl;

@RestController
@RequestMapping("/student")
public class StudentAPI {
    @org.springframework.web.bind.annotation.ExceptionHandler(org.springframework.http.converter.HttpMessageNotReadableException.class)
    public ResponseEntity<ApiResponse> handlerHttpMessageNotReadableException(org.springframework.http.converter.HttpMessageNotReadableException ex){
        return new ResponseEntity<>(new ApiResponse(400,
        "Invalid JSON In Request Body"),HttpStatus.BAD_REQUEST);
    }
    @Autowired
    private StudentServiceImpl studentService;
    @Autowired
    public  Environment environment;
    @GetMapping("/myprofile/{username}")
    public ResponseEntity<Student> getProfile(@PathVariable("username")String username)throws Exception{
        try{
            String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
            ResponseEntity<Student> response = studentService.getMyProfile(decodedUsername);
            return response;
        }catch(Exception exception){
            return new ResponseEntity<>(new Student(),HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/updateprofile")
    public ResponseEntity<ApiResponse> updateProfile(@RequestBody Student student) throws Exception{
        try{
            System.out.println("username" + student.getUsername());
            ResponseEntity<ApiResponse> response = studentService.updateProfile(student);
            return response;
        }catch(Exception exception){
            return new ResponseEntity<>(new ApiResponse(501,
            "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @PostMapping("/applyjob")
    public ResponseEntity<ApiResponse> applyJob(@RequestBody JobApplications jobApplication) throws Exception{
        try{
            ResponseEntity<ApiResponse> response = studentService.applyJob(jobApplication);
                return response;
            }catch(Exception exception){
                return new ResponseEntity<>(new ApiResponse(501,
                "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @PostMapping("/applyinternship")
        public ResponseEntity<ApiResponse> applyInternship(@RequestBody InternShipApplications internshipApplication) throws Exception{
            try{
                ResponseEntity<ApiResponse> response = studentService.applyInternship(internshipApplication);
                return response;
            }catch(Exception exception){
                return new ResponseEntity<>(new ApiResponse(501,
                "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @GetMapping("/myappliedjobs/{username}")
        public ResponseEntity<ApiResponseList> getMyAppliedJobs(@PathVariable("username")String username)throws Exception{
            try{
                System.out.println("username in getMyAppliedJobs:"+username);
                String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
                System.out.println("Decoded Username is getMyAppliedJobs: "+ decodedUsername);
                ResponseEntity<ApiResponseList> response = studentService.getMyAppliedJobs(decodedUsername);
                return response;
            }catch(Exception exception){
                System.out.println("Exception in getMyAppliedJobs:"+ exception.getMessage());
                List<Map<String, String>> noJobs = new ArrayList<>();
                noJobs.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(501,noJobs), HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @GetMapping("/myappliedinternships/{username}")
        public ResponseEntity<ApiResponseList> getMyAppliedInternships(@PathVariable("username")String  username) throws Exception{
            try{
                System.out.println("USername in getMyAppliedJobs: "+username);
                String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
                System.out.println("Decoded username in getMyAppliedJobs:"+ decodedUsername);
                ResponseEntity<ApiResponseList> response = studentService.getMyAppliedInternships(decodedUsername);
                return response;
            }catch(Exception exception){
                System.out.println("Exception in getMyAppliedInternships:"+exception.getMessage());
                List<Map<String,String>> noInternships = new ArrayList<>();
                noInternships.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(501, noInternships),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }
        @GetMapping("/alljobs")
        public ResponseEntity<ApiResponseList> getAllJobs() throws Exception{
            try{
                ResponseEntity<ApiResponseList> response = studentService.getAllJobs();
                return response;
            }catch(Exception exception){
                System.out.println("Exception in getAllJobs:"+ exception.getMessage());
                List<Map<String,String>> noJobs = new ArrayList<>();
                noJobs.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(501, noJobs),HttpStatus.INTERNAL_SERVER_ERROR);
            }
        }

        @GetMapping("/allinternships")
        public ResponseEntity<ApiResponseList> getAllInternships() throws Exception{
        try{
            ResponseEntity<ApiResponseList> response = studentService.getAllInternships();
            return response;
        }catch(Exception exception){
            System.out.println("Exception in getAllInternships:"+ exception.getMessage());
            List<Map<String,String>>noInternships = new ArrayList<>();
            noInternships.add(new HashMap<>());
            return new ResponseEntity<>(new ApiResponseList(501,noInternships),HttpStatus.INTERNAL_SERVER_ERROR);
        }
        }
        @GetMapping("/findjobbyid/{jobId}")
        public ResponseEntity<ApiResponseList> findJobById(@PathVariable("jobId") Integer jobId)throws Exception{
            try{
                ResponseEntity<ApiResponseList> response = studentService.findJobById(jobId);
                return response;
            }catch(Exception exception){
                System.out.println("Exception in findJobById:"+ exception.getMessage());
                List<Map<String,String>> noJobs = new ArrayList<>();
                noJobs.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(501,noJobs),HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }
        @GetMapping("/findinternshipbyid/{internshipId}")
        public ResponseEntity<ApiResponseList> findinternshipById(@PathVariable("internshipId") Integer internshipId)throws Exception{
            try{
                ResponseEntity<ApiResponseList> response = studentService.findInternshipById(internshipId);
                return response;
            }catch(Exception exception){
                System.out.println("Exception in findJobById:"+ exception.getMessage());
                List<Map<String,String>> noJobs = new ArrayList<>();
                noJobs.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(501,noJobs),HttpStatus.INTERNAL_SERVER_ERROR);
        }  
    }
    @GetMapping("/viewjobapplication/{applicationId}/{username}")
    public ResponseEntity<ApiResponseList> viewJobApplicationByStudent( @PathVariable("applicationId")Integer applicationId,
    @PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
        ResponseEntity<ApiResponseList> response =studentService.viewJobApplicationByStudent(applicationId,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in viewJobApplicationByStudent:"+exception.getMessage());
        List<Map<String,String>>noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(501,noApplications),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@GetMapping("/viewinternshipapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponseList>viewinternshipapplicationByStudent(@PathVariable("applicationId") Integer applicationId,
@PathVariable("username")String username)throws Exception{
 try{
    String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
    ResponseEntity<ApiResponseList> response = studentService.viewInternshipApplicationByStudent(applicationId, decodedUsername);
    return response;
 }catch(Exception exception){
   System.out.println("Exception in viewInternshipApplicationByStudent: "+ exception.getMessage());
   List<Map<String, String>> noApplications = new ArrayList<>();
   noApplications.add(new HashMap<>());
   return new ResponseEntity<>(new ApiResponseList(501, noApplications),HttpStatus.INTERNAL_SERVER_ERROR);
 }
}
@PostMapping("/withdrawjobapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse>  withdrawJobApplication(@PathVariable("applicationId") Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
    String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
ResponseEntity<ApiResponse> response = studentService.withdrawJobApplication(applicationId, decodedUsername);
return response;
}catch(Exception exception){
    System.out.println("Exception in withdrawJobApplication:"+ exception.getMessage());
    return new ResponseEntity<>(new ApiResponse(501,
    "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
}
}
@PostMapping("/withdrawinternshipapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse>  withdrawInternshipApplication(@PathVariable("applicationId") Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
    String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
ResponseEntity<ApiResponse> response = studentService.withdrawInternshipApplication(applicationId, decodedUsername);
return response;
}catch(Exception exception){
    System.out.println("Exception in withdrawInternshipApplication:"+ exception.getMessage());
    return new ResponseEntity<>(new ApiResponse(501,
    "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
}
}

@PostMapping("/reapplyjobapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse> reapplyjobapplication(@PathVariable("applicationId")Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
        ResponseEntity<ApiResponse>response = studentService.reapplyJobApplication(applicationId,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in reapplyJobApplication:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);

    }
}
@PostMapping("/reapplyinternshipapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse> reapplyinternshipapplication(@PathVariable("applicationId")Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
        ResponseEntity<ApiResponse>response = studentService.reapplyInternshipApplication(applicationId,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in reapplyInternshipApplication:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"),HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
@DeleteMapping("/deletejobapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse> deleteJobApplication(@PathVariable("applicationId") Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username,"UTF-8");
        ResponseEntity<ApiResponse> response = studentService.deleteJobApplication(applicationId,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in deleteJobApplication:"+ exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal Server Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

@DeleteMapping("/deleteinternshipapplication/{applicationId}/{username}")
public ResponseEntity<ApiResponse> deleteInternshipApplication(@PathVariable("applicationId")Integer applicationId,
@PathVariable("username")String username)throws Exception{
    try{
        String decodedUsername = java.net.URLDecoder.decode(username, "UTF-8");
        ResponseEntity<ApiResponse> response = studentService.deleteInternshipApplication(applicationId,decodedUsername);
        return response;
    }catch(Exception exception){
        System.out.println("Exception in delteInternshipApplication:"+exception.getMessage());
        return new ResponseEntity<>(new ApiResponse(501,
        "Internal SErver Error"), HttpStatus.INTERNAL_SERVER_ERROR);
    }
}

}