package com.zidio.project.dao;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.zidio.project.entity.InternshipApplicationsEntity;
import com.zidio.project.entity.InternshipEntity;
import com.zidio.project.entity.JobApplicationsEntity;
import com.zidio.project.entity.JobsEntity;
import com.zidio.project.entity.StudentEntity;
import com.zidio.project.model.Student;
import com.zidio.project.api.ApiResponse;
import com.zidio.project.api.ApiResponseList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;


@Repository(value="StudentDAO")
public class StudentDAOImpl {
    @Autowired
    private CrudRepository<StudentEntity, String> studentRepository;

    @Autowired
    private CrudRepository<InternshipApplicationsEntity, Integer> internshipApplicationsEntity;

    @Autowired
    private CrudRepository<JobApplicationsEntity, Integer> jobApplicationsEntity;

    @Autowired
    private CrudRepository<JobsEntity, Integer> jobsRepository;

    @Autowired
    private CrudRepository<InternshipEntity, Integer> internshipRepository;

    public ResponseEntity<Student> getMyProfile(String username)throws Exception{
        StudentEntity studentEntity = studentRepository.findById(username).orElse(null);
        if(studentEntity != null){
            Student student = new Student();
            student.setUsername(studentEntity.getUsername());
            student.setName(studentEntity.getName());
            student.setMobilenumber(studentEntity.getMobilenumber());
            student.setEducation(studentEntity.getEducation());
            student.setSkills(studentEntity.getSkills());
            student.setLocation(studentEntity.getLocation());
            student.setImage(studentEntity.getImage());
            student.setResume(studentEntity.getResume());
            return new ResponseEntity<>(student, HttpStatus.OK);
        }else{
            Student student = new Student();
            return new ResponseEntity<>(student, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<ApiResponse> updateProfile(Student student)throws Exception{
        StudentEntity existingStudent = studentRepository.findById(student.getUsername()).orElse(null);
        if(existingStudent != null){
            StudentEntity studentEntity = new StudentEntity();
            studentEntity.setUsername(student.getUsername());
            studentEntity.setName(student.getName());
            studentEntity.setMobilenumber(student.getMobilenumber());
            studentEntity.setEducation(student.getEducation());
            studentEntity.setSkills(student.getSkills());
            studentEntity.setLocation(student.getLocation());
            studentEntity.setImage(student.getImage());
            studentEntity.setResume(student.getResume());
            studentRepository.save(studentEntity);
            return new ResponseEntity<>(new ApiResponse(200,
            "Profile updated successfully"), HttpStatus.OK);

        }else{
            return new ResponseEntity<>(new ApiResponse(404,
            "Student does not exist"), HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<ApiResponse> applyJob(int jobId, String username)throws Exception{
        JobsEntity existingJob = jobsRepository.findById(jobId).orElse(null);
        if(existingJob != null){
            Iterable<JobApplicationsEntity> allApplication = jobApplicationsEntity.findAll();
            for(JobApplicationsEntity application : allApplication){
                if(application.getJobid() == jobId && application.getAppliedby().equals(username)){
                    return new ResponseEntity<>(new ApiResponse(409,
                    "Already applied for this job"), HttpStatus.CONFLICT);
                }
            }
            JobApplicationsEntity jobApplication = new JobApplicationsEntity();
            jobApplication.setJobid(jobId);
            jobApplication.setAppliedby(username);
            jobApplication.setStatus("applied");
            LocalDateTime now = LocalDateTime.now();
            String formattedDate = now.toString();
            jobApplication.setAppliedtime(formattedDate);
            JobApplicationsEntity entity = jobApplicationsEntity.save(jobApplication);
            return new ResponseEntity<>(new ApiResponse(200,
            "Job application submitted successfully with job applicationid:"+
            entity.getApplicationid()), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(404,
            "Job does not exist"), HttpStatus.NOT_FOUND);
        }
    }

    public ResponseEntity<ApiResponse> applyInternship(int internshipId, String username)throws Exception{
        InternshipEntity existingInternship = internshipRepository.findById(internshipId).orElse(null);
        if(existingInternship != null){
            Iterable<InternshipApplicationsEntity> allApplications = internshipApplicationsEntity.findAll();
            for(InternshipApplicationsEntity application : allApplications){
                if(application.getInternshipid() == internshipId && application.getAppliedby().equals(username)){
                return new ResponseEntity<>(new ApiResponse(409,
                "Already applied for this internship"), HttpStatus.CONFLICT);
            }
        }
        InternshipApplicationsEntity internshipApplication =  new InternshipApplicationsEntity();
        internshipApplication.setInternshipid(internshipId);
        internshipApplication.setAppliedby(username);
        internshipApplication.setStatus("applied");
        LocalDateTime now = LocalDateTime.now();
        String formattedDate = now.toString();
        internshipApplication.setAppliedtime(formattedDate);
        InternshipApplicationsEntity entity = internshipApplicationsEntity.save(internshipApplication);
        return new ResponseEntity<>(new ApiResponse(200,
        "Internship application submitted successfully with internhsip application id: " + 
        entity.getApplicationid()), HttpStatus.OK);
    }else{
        return new ResponseEntity<>(new ApiResponse(404,
        "Internship does not exist"), HttpStatus.NOT_FOUND);
    }
}
public ResponseEntity<ApiResponseList> getMyAppliedJobs(String username)throws Exception{
    System.out.println("Fetching applied jobs user: " +username);
    Iterable<JobApplicationsEntity> allApplication = jobApplicationsEntity.findAll();
    List<Map<String, String>>appliedJobs = new ArrayList<>();
    for(JobApplicationsEntity application : allApplication){
        if(application.getAppliedby().equals(username)){
            Map<String,String> jobDetails = new HashMap<>();
            jobDetails.put("applicationId", String.valueOf(application.getApplicationid()));
            jobDetails.put("jobId", String.valueOf(application.getJobid()));
            jobDetails.put("status", application.getStatus());
            jobDetails.put("applicationTime", application.getAppliedtime());
            JobsEntity job =jobsRepository.findById(application.getJobid()).orElse(null);
            if(job != null){
                jobDetails.put("jobId", String.valueOf(job.getJobId()));
                jobDetails.put("jobTitle", job.getJobTitle());
                jobDetails.put("companyName", job.getCompanyName());
                jobDetails.put("location", job.getLocation());
                jobDetails.put("salaryRange", String.valueOf(job.getSalaryRange()));
                jobDetails.put("jobType", job.getJobType());
                jobDetails.put("experienceRequired",job.getExperienceRequired());
                jobDetails.put("skillsRequired", job.getSkillsRequired());
                jobDetails.put("recruiterUsername",job.getRecruiterUsername());
                jobDetails.put("datePosted", job.getDatePosted());
                jobDetails.put("applicationDeadline",job.getApplicationDeadline());
                jobDetails.put("educationRequired", job.getEducationRequired());
                jobDetails.put("companyWebsite", job.getCompanyWebsite());
                jobDetails.put("jobDescription",job.getJobDescription());
                
            }
            appliedJobs.add(jobDetails);
        }
    }

    if(!appliedJobs.isEmpty()){
        return new ResponseEntity<>(new ApiResponseList(200, appliedJobs),HttpStatus.OK);
    }else {
        List<Map<String, String>> noJobs = new ArrayList<>();
        noJobs.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404,noJobs),HttpStatus.NOT_FOUND);
    }
    
}
    public ResponseEntity<ApiResponseList> getMyAppliedInternships(String username)throws Exception{
        System.out.println("Fetching applied internships for user:" +username);
        Iterable<InternshipApplicationsEntity>allApplications = internshipApplicationsEntity.findAll();
        List<Map<String, String>> appliedInternships = new ArrayList<>();
        for(InternshipApplicationsEntity application : allApplications){
            if(application.getAppliedby().equals(username)){
                Map<String,String> internshipDetails = new HashMap<>();
                internshipDetails.put("applicationId", String.valueOf(application.getApplicationid()));
                internshipDetails.put("internshipId", String.valueOf(application.getInternshipid()));
                internshipDetails.put("status", application.getStatus());
                internshipDetails.put("appliedTime", application.getAppliedtime());
                InternshipEntity internship = internshipRepository.findById(application.getInternshipid()).orElse(null);
                if(internship != null){
                    internshipDetails.put("internhsipId", String.valueOf(internship.getInternshipId()));
                    internshipDetails.put("internshipTitle", internship.getInternshipTitle());
                    internshipDetails.put("companyName", internship.getCompanyName());
                    internshipDetails.put("location",internship.getLocation());
                    internshipDetails.put("stipend", String.valueOf(internship.getStipend()));
                    internshipDetails.put("startDate", internship.getStartDate());
                    internshipDetails.put("endDate", internship.getEndDate());
                    internshipDetails.put("skillsRequired",internship.getSkillsRequired());
                    internshipDetails.put("recruiterUsername", internship.getRecruiterUsername());
                    internshipDetails.put("datePosted", internship.getDatePosted());
                    internshipDetails.put("applicationDeadline", internship.getApplicationDeadline());
                    internshipDetails.put("educationRequired", internship.getEducationRequired());
                    internshipDetails.put("companyWebsite", internship.getCompanyWebsite());
                    internshipDetails.put("internshipDescription", internship.getInternshipDescription());
                }
                appliedInternships.add(internshipDetails);
            }

        }
        if(!appliedInternships.isEmpty()){
            return new ResponseEntity<>(new ApiResponseList(200,appliedInternships),HttpStatus.OK);
        }else{
            List<Map<String, String>> noInternships = new ArrayList<>();
            return new ResponseEntity<>(new ApiResponseList(404, noInternships), HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<ApiResponseList> getAllJobs()throws Exception{
        System.out.println("Fetching all jobs:" );
        Iterable<JobsEntity>allJobs = jobsRepository.findAll();
        List<Map<String, String>> jobList = new ArrayList<>();
            for(JobsEntity job : allJobs){
                Map<String,String> jobDetails = new HashMap<>();
                jobDetails.put("jobId", String.valueOf(job.getJobId()));
                jobDetails.put("jobTitle", job.getJobTitle());
                jobDetails.put("jobDescription", job.getJobDescription());
                jobDetails.put("companyName",job.getCompanyName());
                jobDetails.put("companyWebsite", String.valueOf(job.getCompanyWebsite()));
                jobDetails.put("location", job.getLocation());
                jobDetails.put("salaryRange", String.valueOf(job.getSalaryRange()));
                jobDetails.put("skillsRequired",job.getSkillsRequired());
                jobDetails.put("recruiterUsername", job.getRecruiterUsername());
                jobDetails.put("datePosted", job.getDatePosted());
                jobDetails.put("applicationDeadline",job.getApplicationDeadline());
                jobDetails.put("educationRequired", job.getEducationRequired());
                jobDetails.put("experienceRequired", job.getExperienceRequired());
                jobDetails.put("jobType", job.getJobType());
                jobList.add(jobDetails);
            }
            if(!jobList.isEmpty()){
                return new ResponseEntity<>(new ApiResponseList(200,jobList),HttpStatus.OK);
            }else{
                List<Map<String, String>> noJobs = new ArrayList<>();
                noJobs.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(404, noJobs),HttpStatus.NOT_FOUND);
            }
        }
        public ResponseEntity<ApiResponseList> getAllInternships()throws Exception{
        System.out.println("Fetching all Internhsips:" );
        Iterable<InternshipEntity>allInternships = internshipRepository.findAll();
        List<Map<String, String>> internshipList = new ArrayList<>();
        for(InternshipEntity internhsip : allInternships){
        Map<String,String> internshipDetails = new HashMap<>();
                
                    internshipDetails.put("internshipId", String.valueOf(internhsip.getInternshipId()));
                    internshipDetails.put("internshipTitle", internhsip.getInternshipTitle());
                    internshipDetails.put("internshipDescription", internhsip.getInternshipDescription());
                    internshipDetails.put("companyName",internhsip.getCompanyName());
                    internshipDetails.put("companyWebsite",internhsip.getCompanyWebsite());
                    internshipDetails.put("location", internhsip.getLocation());
                    internshipDetails.put("stipend", String.valueOf(internhsip.getStipend()));
                    internshipDetails.put("skillsRequired",internhsip.getSkillsRequired());
                    internshipDetails.put("recruiterUsername", internhsip.getRecruiterUsername());
                    internshipDetails.put("datePosted", internhsip.getDatePosted());
                    internshipDetails.put("applicationDeadline",internhsip.getApplicationDeadline());
                    internshipDetails.put("educationRequired", internhsip.getEducationRequired());
                    internshipDetails.put("datePosted", internhsip.getDatePosted());
                    internshipDetails.put("stipend", internhsip.getStipend());
                    internshipDetails.put("startDate", internhsip.getStartDate());
                    internshipDetails.put("endDate", internhsip.getEndDate());
                    internshipList.add(internshipDetails);
                }
                if(!internshipList.isEmpty()){
                    return new ResponseEntity<>(new ApiResponseList(200,internshipList),HttpStatus.OK);
                }else{
                    List<Map<String, String>> noInternshipsList = new ArrayList<>();
                    noInternshipsList.add(new HashMap<>());
                    return new ResponseEntity<>(new ApiResponseList(404, noInternshipsList),HttpStatus.NOT_FOUND);
            }
        }
        public ResponseEntity<ApiResponseList> getAllJobsPostedByMe(String reqruiterUsername)throws Exception{
            System.out.println("Fetching all jobs");
            Iterable<JobsEntity>allJobs = jobsRepository.findAll();
            List<Map<String, String>> jobList = new ArrayList<>();
            for(JobsEntity job : allJobs){
                if(!job.getRecruiterUsername().equals(reqruiterUsername)){
                    continue;
                }
                Map<String, String> jobDetails = new HashMap<>();
                   jobDetails.put("jobId", String.valueOf(job.getJobId()));
                    jobDetails.put("jobTitle", job.getJobTitle());
                    jobDetails.put("jobDescription", job.getJobDescription());
                    jobDetails.put("companyName",job.getCompanyName());
                    jobDetails.put("companyWebsite", String.valueOf(job.getCompanyWebsite()));
                    jobDetails.put("location", job.getLocation());
                    jobDetails.put("salaryRange", String.valueOf(job.getSalaryRange()));
                    jobDetails.put("jobType", job.getJobType());
                    jobDetails.put("experienceRequired", job.getExperienceRequired());
                    jobDetails.put("skillsRequired",job.getSkillsRequired());
                    jobDetails.put("recruiterUsername", job.getRecruiterUsername());
                    jobDetails.put("datePosted", job.getDatePosted());
                    jobDetails.put("applicationDeadline",job.getApplicationDeadline());
                    jobDetails.put("educationRequired", job.getEducationRequired());

                    jobList.add(jobDetails);
                }
                if(!jobList.isEmpty()){
                    return new ResponseEntity<>(new ApiResponseList(200, jobList), HttpStatus.OK);
                }else{
                    List<Map<String, String>> noJobs = new ArrayList<>();
                    noJobs.add(new HashMap<>());
                    return new ResponseEntity<>(new ApiResponseList(404,noJobs), HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponseList> getAllInternshipsPostedByMe(String recruiterUsername)throws Exception{
                System.out.println("Fetching all Internships for username:"+ recruiterUsername);
                Iterable<InternshipEntity> allInternships = internshipRepository.findAll();
                List<Map<String, String>> internshipList = new ArrayList<>();
                for(InternshipEntity internship : allInternships){
                    if(!internship.getRecruiterUsername().equals(recruiterUsername)){
                        continue;
                    }
                    Map<String, String> internshipDetails = new HashMap<>();
                    internshipDetails.put("internshipId", String.valueOf(internship.getInternshipId()));
                    internshipDetails.put("internshipTitle", internship.getInternshipTitle());
                    internshipDetails.put("internshipDescription", internship.getInternshipDescription());
                    internshipDetails.put("companyName",internship.getCompanyName());
                    internshipDetails.put("companyWebsite",internship.getCompanyWebsite());
                    internshipDetails.put("location", internship.getLocation());
                    internshipDetails.put("stipend", String.valueOf(internship.getStipend()));
                    internshipDetails.put("skillsRequired",internship.getSkillsRequired());
                    internshipDetails.put("recruiterUsername", internship.getRecruiterUsername());
                    internshipDetails.put("datePosted", internship.getDatePosted());
                    internshipDetails.put("applicationDeadline",internship.getApplicationDeadline());
                    internshipDetails.put("educationRequired", internship.getEducationRequired());
                    internshipDetails.put("startDate", internship.getStartDate());
                    internshipDetails.put("endDate", internship.getEndDate());
                    internshipList.add(internshipDetails);
                }
                if(!internshipList.isEmpty()){
                    return new ResponseEntity<>(new ApiResponseList(200, internshipList), HttpStatus.OK);
                }else{
                    List<Map<String, String>> noInternshipList = new ArrayList<>();
                    return new ResponseEntity<>(new ApiResponseList(404, noInternshipList), HttpStatus.NOT_FOUND);
                }
                }

                public ResponseEntity<ApiResponseList> findJobById(int jobId)throws Exception{
                    JobsEntity job = jobsRepository.findById(jobId).orElse(null);
                    if(job != null){
                        Map<String, String > jobDetails = new HashMap<>();
                   jobDetails.put("jobId", String.valueOf(job.getJobId()));
                    jobDetails.put("jobTitle", job.getJobTitle());
                    jobDetails.put("jobDescription", job.getJobDescription());
                    jobDetails.put("comapnyName",job.getCompanyName());
                    jobDetails.put("comapnyWebsite", String.valueOf(job.getCompanyWebsite()));
                    jobDetails.put("location", job.getLocation());
                    jobDetails.put("salaryRange", String.valueOf(job.getSalaryRange()));
                    jobDetails.put("jobType", job.getJobType());
                    jobDetails.put("experienceRequired", job.getExperienceRequired());
                    jobDetails.put("skillsRequired",job.getSkillsRequired());
                    jobDetails.put("recruiterUsername", job.getRecruiterUsername());
                    jobDetails.put("datePosted", job.getDatePosted());
                    jobDetails.put("applicationDeadline",job.getApplicationDeadline());
                    jobDetails.put("educationRequired", job.getEducationRequired());
                    List<Map<String, String>> responseList = new ArrayList<>();
                    responseList.add(jobDetails);
                    return new ResponseEntity<>(new ApiResponseList(200,responseList), HttpStatus.OK);
                    }else{
                        List<Map<String, String>> noJobs = new ArrayList<>();
                        noJobs.add(new HashMap<>());
                        return new ResponseEntity<>(new ApiResponseList(404, noJobs), HttpStatus.NOT_FOUND);
                    }
                }
                public ResponseEntity<ApiResponseList> findInternshipById(int internshipId)throws Exception{
                    InternshipEntity internship = internshipRepository.findById(internshipId).orElse(null);
                    if(internship != null){
                        Map<String, String> internshipDetails = new HashMap<>();
                    internshipDetails.put("internshipId", String.valueOf(internship.getInternshipId()));
                    internshipDetails.put("internshipTitle", internship.getInternshipTitle());
                    internshipDetails.put("internshipDescription", internship.getInternshipDescription());
                    internshipDetails.put("comapnyName",internship.getCompanyName());
                    internshipDetails.put("comapnyWebsite",internship.getCompanyWebsite());
                    internshipDetails.put("location", internship.getLocation());
                    internshipDetails.put("stipend", String.valueOf(internship.getStipend()));
                    internshipDetails.put("skillsRequired",internship.getSkillsRequired());
                    internshipDetails.put("recruiterUsername", internship.getRecruiterUsername());
                    internshipDetails.put("datePosted", internship.getDatePosted());
                    internshipDetails.put("applicationDeadline",internship.getApplicationDeadline());
                    internshipDetails.put("educationRequired", internship.getEducationRequired());
                    internshipDetails.put("StartDate", internship.getStartDate());
                    internshipDetails.put("EndDate", internship.getEndDate());
                    List<Map<String,String>> responseList = new ArrayList<>();
                    responseList.add(internshipDetails);
                    return new ResponseEntity<>(new ApiResponseList(200, responseList), HttpStatus.OK);
                    }else{
                       List<Map<String,String>> noInternships = new ArrayList<>();
                       noInternships.add(new HashMap<>());
                       return new ResponseEntity<>(new ApiResponseList(404, noInternships), HttpStatus.NOT_FOUND);
                    }
                }
                public ResponseEntity<ApiResponseList> getJobApplicationForMyJobId(int jobId, String recruiterUsername)throws Exception{
                    JobsEntity job = jobsRepository.findById(jobId).orElse(null);
                    if(job ==  null || !job.getRecruiterUsername().equals(recruiterUsername)){
                    List<Map<String, String>> noApplications = new ArrayList<>();
                    noApplications.add(new HashMap<>());
                    return new ResponseEntity<>(new ApiResponseList(404, noApplications), HttpStatus.NOT_FOUND);

                }
                System.out.println("Fetching job applications for job Id:" +jobId);
                Iterable<JobApplicationsEntity> allApplications = jobApplicationsEntity.findAll();
                List<Map<String, String>> jobApplicationsList = new ArrayList<>();
                for(JobApplicationsEntity application : allApplications){
                    if(application.getJobid() == jobId){
                        Map<String, String> applicationDetails = new HashMap<>();
                        applicationDetails.put("applicationId", String.valueOf(application.getApplicationid()));
                        applicationDetails.put("appliedBy", application.getAppliedby());
                        applicationDetails.put("status", application.getStatus());
                        applicationDetails.put("appliedTime", application.getAppliedtime());
                        jobApplicationsList.add(applicationDetails);
                    }
                }
                if(!jobApplicationsList.isEmpty()){
                    return new ResponseEntity<>(new ApiResponseList(200, jobApplicationsList), HttpStatus.OK);
                }else {
                    List<Map<String, String>> noApplications = new ArrayList<>();
                    noApplications.add(new HashMap<>());
                    return new ResponseEntity<>(new ApiResponseList(404, noApplications), HttpStatus.NOT_FOUND);
                }
        }
        public ResponseEntity<ApiResponseList> getInternshipApplicationsForMyInternshipId(int internshipId, String recruiterUsername)throws Exception{
            InternshipEntity internhsip = internshipRepository.findById(internshipId).orElse(null);
            if(internhsip == null || !internhsip.getRecruiterUsername().equals(recruiterUsername)){
                List<Map<String, String>> noApplications = new ArrayList<>();
                noApplications.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(404, noApplications), HttpStatus.NOT_FOUND);
            }
            System.out.println("Fetching internship applications for internship ID:" +internshipId);
            Iterable<InternshipApplicationsEntity> allApplications = internshipApplicationsEntity.findAll();
            List<Map<String, String>> internshipApplicationList = new ArrayList<>();
            for(InternshipApplicationsEntity application : allApplications){
                if(application.getInternshipid() == internshipId){
                    Map<String, String> applicationDetails = new HashMap<>();
                    applicationDetails.put("applicationId", String.valueOf(application.getApplicationid()));
                    applicationDetails.put("appliedBy", application.getAppliedby());
                    applicationDetails.put("status", application.getStatus());
                    applicationDetails.put("appliedTime", application.getAppliedtime());
                    internshipApplicationList.add(applicationDetails);
                }
            }
            if(!internshipApplicationList.isEmpty()){
                return new ResponseEntity<>(new ApiResponseList(200, internshipApplicationList),HttpStatus.OK);

            }else{
                List<Map<String,String>> noApplications = new ArrayList<>();
                noApplications.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(404, noApplications),HttpStatus.NOT_FOUND);
            }
        }
    public ResponseEntity<ApiResponseList> viewJobApplication(int applicationId, String recruiterUsername)throws Exception{
        System.out.println("Fetching job application ID : "+applicationId);
        System.out.println("Recruiter Username:"+recruiterUsername);
        JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
        if(application != null){
            JobsEntity job = jobsRepository.findById(application.getJobid()).orElse(null);
            if(job == null || !job.getRecruiterUsername().equals(recruiterUsername)){
                List<Map<String, String>> noApplications = new ArrayList<>();
                noApplications.add(new HashMap<>());
                return new ResponseEntity<>(new ApiResponseList(404, noApplications),HttpStatus.NOT_FOUND);
            }
            if(!application.getStatus().equals("selected") && !application.getStatus().equals("rejected")&& 
            !application.getStatus().equals("withdrawn")){
                application.setStatus("viewed by recruiter");
                jobApplicationsEntity.save(application);
            }
            Map<String, String> applicationDetails = new HashMap<>();
            applicationDetails.put("applicationId", String.valueOf(application.getApplicationid()));
            applicationDetails.put("jobId", String.valueOf(application.getJobid()));
            applicationDetails.put("appliedBy", application.getAppliedby());
            applicationDetails.put("status", application.getStatus());
            applicationDetails.put("appliedTime", application.getAppliedtime());
            StudentEntity student = studentRepository.findById(application.getAppliedby()).orElse(null);
            if(student != null){
                applicationDetails.put("studentName", student.getName());
                applicationDetails.put("studentMobileNumber", String.valueOf(student.getMobilenumber()));
                applicationDetails.put("studentEducation", student.getSkills());
                applicationDetails.put("studentSkills", student.getSkills());
                applicationDetails.put("studentLocation", student.getLocation());
                applicationDetails.put("studentImage", student.getLocation());
                applicationDetails.put("studentResume", student.getResume());

        }
        List<Map<String, String>> responseList = new ArrayList<>();
        responseList.add(applicationDetails);
        return new ResponseEntity<>(new ApiResponseList(200, responseList), HttpStatus.OK);
    }else{
        List<Map<String, String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404, noApplications), HttpStatus.NOT_FOUND);

    }
}
public ResponseEntity<ApiResponseList> viewJobApplicationByStudent(int applicationId, String studentUsername)throws Exception{
    JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
    if(application != null && application.getAppliedby().equals(studentUsername)){
        Map<String, String> applicationDetails = new HashMap<>();
        applicationDetails.put("applicationId", String.valueOf(application.getApplicationid()));
        applicationDetails.put("jobId", String.valueOf(application.getJobid()));
        applicationDetails.put("appliedBy", application.getAppliedby());
        applicationDetails.put("status", application.getStatus());
        applicationDetails.put("appliedTime", application.getAppliedtime());
        List<Map<String, String>> responseList = new ArrayList<>();
        responseList.add(applicationDetails);
        return new ResponseEntity<>(new ApiResponseList(200, responseList), HttpStatus.OK);

    }else{
        List<Map<String, String>> noApplications = new ArrayList<>();
        noApplications.add(new HashMap<>());
        return new ResponseEntity<>(new ApiResponseList(404, noApplications), HttpStatus.NOT_FOUND);
    }
}
public ResponseEntity<ApiResponse> withdrawJobApplication(int applicationId, String studentUsername)throws Exception{
    JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
    if(application != null && application.getAppliedby().equals(studentUsername)){
        if(!application.getStatus().equals("selected") && !application.getStatus().equals("rejeted")){
            application.setStatus("withdrawn");
            jobApplicationsEntity.save(application);
            return new ResponseEntity<>(new ApiResponse(200, 
            "Job application withdrawn successfully"), HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(400, 
            "Cannot withdraw a selected or rejected successfully"), HttpStatus.BAD_REQUEST);
        }
    }else{
            return new ResponseEntity<>(new ApiResponse(404,
            "Application not found"), HttpStatus.NOT_FOUND);
    }
}
    public ResponseEntity<ApiResponse> withdrawInternshipApplication(int applicationId, String studentUsername)throws Exception{
        InternshipApplicationsEntity application = internshipApplicationsEntity.findById(applicationId).orElse(null);
        if(application != null && application.getAppliedby().equals(studentUsername)){
            if(!application.getStatus().equals("Selected")&& !application.getStatus().equals("Rejected")){
                application.setStatus("withdrawn");
                internshipApplicationsEntity.save(application);
                return new ResponseEntity<>(new ApiResponse(200,
                "Internship application withdrawn successfully"), HttpStatus.OK);
            }else{
                return new ResponseEntity<>(new ApiResponse(400,
                "Cannot withdraw a selected or rejected application"), HttpStatus.BAD_REQUEST);
            }
        }else{
                return new ResponseEntity<>(new ApiResponse(404,
                "Application not found"), HttpStatus.NOT_FOUND);
            }
        
    }
        public ResponseEntity<ApiResponse> reapplyJobApplication(int applicationId, String studentUsername) throws Exception{
        JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
        if(application  != null && application.getAppliedby(). equals(studentUsername)){
            if(application.getStatus().equals("withdrawn")){
                application.setStatus("applied");
                LocalDateTime now = LocalDateTime.now();
                String formattedDate = now.toString();
                application.setAppliedtime(formattedDate);
                jobApplicationsEntity.save(application);
                return new ResponseEntity<>(new ApiResponse(200,
                "Job application re-applied sucessfully"), HttpStatus.OK);

            }else{
                return new ResponseEntity<>(new ApiResponse(400,
                "cannot reapply for a non-withdrawn application"), HttpStatus.BAD_REQUEST);
            }
        }else{
                return new ResponseEntity<>(new ApiResponse(404,
                "Application not found."), HttpStatus.NOT_FOUND);
            }
    }
        public ResponseEntity<ApiResponse> reapplyInternshipApplication(int applicationId, String studentUsername)throws Exception{
            InternshipApplicationsEntity application = internshipApplicationsEntity.findById(applicationId).orElse(null);
            if(application != null && application .getAppliedby().equals(studentUsername)){
                if(application.getStatus().equals("withdrawn")){
                    application.setStatus("applied");
                    LocalDateTime now = LocalDateTime.now();
                    String formattedDate = now.toString();
                    application.setAppliedtime(formattedDate);
                    internshipApplicationsEntity.save(application);
                    return new ResponseEntity<>(new ApiResponse(200,
                    "Internship application re-applied successfully"), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new ApiResponse(400,
                    "Application  not found"), HttpStatus.BAD_REQUEST);
                }
            }else{
                    return new ResponseEntity<>(new ApiResponse(400,
                    "Application  not found"), HttpStatus.NOT_FOUND);
            }
        }
            public ResponseEntity<ApiResponse> deleteJobApplication(int applicationId, String studentUsername)throws Exception{
                JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null && application.getAppliedby().equals(studentUsername)){
                if(!application.getStatus().equals("selected") && !application.getStatus().equals("rejected")){
                    jobApplicationsEntity.delete(application);
                    return new ResponseEntity<>(new ApiResponse(200,
                    "Job application deleted successfully"), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new ApiResponse(400,
                    "Cannot delete a selected or rejected application"), HttpStatus.BAD_REQUEST);

                }
            }else{
                return new ResponseEntity<>(new ApiResponse(404,
                "Application not found"), HttpStatus.NOT_FOUND);
            }
            }
            public ResponseEntity<ApiResponse> deleteJobApplicationByRecruiter(int applicationId, String studentUsername)throws Exception{
                JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null && application.getAppliedby().equals(studentUsername)){
                    jobApplicationsEntity.delete(application);
                    return new ResponseEntity<>(new ApiResponse(200,
                    "Job application deleted successfully"), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new ApiResponse(404,
                    "Application not found."), HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponse> deleteInternshipApplication(int applicationId, String studentUsername)throws Exception{
                InternshipApplicationsEntity application = internshipApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null && application.getAppliedby().equals(studentUsername)){
                    if(!application.getStatus().equals("selected") && !application.getStatus().equals("rejected")){
                       internshipApplicationsEntity.delete(application);
                       return new ResponseEntity<>(new ApiResponse(200,
                       "Internship application deleted successfully"), HttpStatus.OK);
                    }else{
                        return new ResponseEntity<>(new ApiResponse(400,
                        "Cannot delete a selected or rejected application"),HttpStatus.BAD_REQUEST);
                    }
                }else{
                    return new ResponseEntity<>(new ApiResponse(404, 
                    "Application not found."), HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponse> deleteInternshipApplicationByRecruiter(int applicationId, String studentUsername)throws Exception{
                InternshipApplicationsEntity application= internshipApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null && application.getAppliedby().equals(studentUsername)){
                    internshipApplicationsEntity.delete(application);
                    return new ResponseEntity<>(new ApiResponse(200,
                    "Internhship application deleted successfully"), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new ApiResponse(404,
                    "Application not found"),HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponseList> viewInternshipApplication(int applicationId, String recruiterUsername)throws Exception{
                InternshipApplicationsEntity application = internshipApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null){
                    InternshipEntity internship = internshipRepository.findById(application.getInternshipid()).orElse(null);
                    if(internship == null || !internship.getRecruiterUsername().equals(recruiterUsername)){
                        List<Map<String, String>> noApplications = new ArrayList<>();
                        noApplications.add(new HashMap<>());
                        return new ResponseEntity<>(new ApiResponseList(404, noApplications),HttpStatus.NOT_FOUND);

                    }
                    if(!application.getStatus().equals("selected") && !application.getStatus().equals("rejected") && 
                    !application.getStatus().equals("withdrawn")){
                        application.setStatus("viewed by recruiter");
                        internshipApplicationsEntity.save(application);
                    }
                    Map<String, String> applicationDetails = new HashMap<>();
                    applicationDetails.put("applicationId", String.valueOf(application.getApplicationid()));
                    applicationDetails.put("internshipId", String.valueOf(application.getInternshipid()));
                    applicationDetails.put("appliedBy", application.getAppliedby());
                    applicationDetails.put("status", application.getStatus());
                    applicationDetails.put("appliedTime", application.getAppliedtime());
                    StudentEntity student = studentRepository.findById(application.getAppliedby()).orElse(null);
                    if(student != null){
                        applicationDetails.put("studnetName", student.getName());
                        applicationDetails.put("StudentMobileNumber", String.valueOf(student.getMobilenumber()));
                        applicationDetails.put("studentEducation", student.getEducation());
                        applicationDetails.put("studentSkills", student.getSkills());
                        applicationDetails.put("studentLocation", student.getLocation());
                        applicationDetails.put("studentImage", student.getImage());
                        applicationDetails.put("studentResume", student.getResume());

                    }
                    List<Map<String,String>> responseList = new ArrayList<>();
                    responseList.add(applicationDetails);
                    return new ResponseEntity<>(new ApiResponseList(200, responseList),HttpStatus.OK);

                }else{
                    List<Map<String, String>> noApplications =  new ArrayList<>();
                    noApplications.add(new HashMap<>());
                    return new ResponseEntity<>(new ApiResponseList(404, noApplications),HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponseList> viewInternshipApplicationByStudent(int applicationId, String studentUsername)throws Exception{
                InternshipApplicationsEntity application = internshipApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null && application.getAppliedby().equals(studentUsername)){
                    Map<String,String> applicationDetails = new HashMap<>();
                    applicationDetails.put("applicationId", String.valueOf(application.getApplicationid()));
                    applicationDetails.put("internshipId", String.valueOf(application.getInternshipid()));
                    applicationDetails.put("appliedBy", application.getAppliedby());
                    applicationDetails.put("status", application.getStatus());
                    applicationDetails.put("appliedTime", application.getAppliedtime());
                    List<Map<String,String>> responseList = new ArrayList<>();
                    responseList.add(applicationDetails);
                    return new ResponseEntity<>(new ApiResponseList(200, responseList), HttpStatus.OK);

                }else{
                    List<Map<String, String>> noApplications = new ArrayList<>();
                    noApplications.add(new HashMap<>());
                    return new ResponseEntity<>(new ApiResponseList(404,noApplications),HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponse> selectJobApplication(int applicationId, String recruiterUsername)throws Exception{
                JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null ){
                    JobsEntity job = jobsRepository.findById(application.getJobid()).orElse(null);
                    if(job == null || !job.getRecruiterUsername().equals(recruiterUsername)){
                        return new ResponseEntity<>(new ApiResponse(404,"Application not found"),HttpStatus.NOT_FOUND);
                    }
                    application.setStatus("selected");
                    jobApplicationsEntity.save(application);
                    return new ResponseEntity<>(new ApiResponse(200, 
                    "Job application selected successfully"), HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new ApiResponse(404,
                    "Application not found"),HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponse> selectInternshipApplication(int applicationId,String recruiterUsername)throws Exception{
                InternshipApplicationsEntity application = internshipApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null ){
                    InternshipEntity internship = internshipRepository.findById(application.getInternshipid()).orElse(null);
                    if(internship == null || !internship.getRecruiterUsername(). equals(recruiterUsername)){
                        return new ResponseEntity<>(new ApiResponse(404,"Application not Found"),HttpStatus.NOT_FOUND);
                    }
                    application.setStatus("selected");
                    internshipApplicationsEntity.save(application);
                    return new ResponseEntity<>(new ApiResponse(200,
                    "Internship application selected successfully"),HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new ApiResponse(404,
                    "Application not found"),HttpStatus.NOT_FOUND);
                }
            }
            public ResponseEntity<ApiResponse> rejectJobApplication(int applicationId, String recruiterUsername)throws Exception{
                JobApplicationsEntity application = jobApplicationsEntity.findById(applicationId).orElse(null);
                if(application != null){
                    JobsEntity job = jobsRepository.findById(application.getJobid()).orElse(null);
                    if(job == null || !job.getRecruiterUsername().equals(recruiterUsername)){
                        return new ResponseEntity<>(new ApiResponse(404,"Apllication not found"), HttpStatus.NOT_FOUND);
                    }
                    application.setStatus("rejected");
                    jobApplicationsEntity.save(application);
                    return new ResponseEntity<>(new ApiResponse(200,
                    "Job application rejected successfully"),HttpStatus.OK);
                }else{
                    return new ResponseEntity<>(new ApiResponse(404,
                    "Application not found"), HttpStatus.NOT_FOUND);
                }
            }
                public ResponseEntity<ApiResponse> rejectInternshipApplication(int applicationId, String recruiterUsername)throws Exception{
                    InternshipApplicationsEntity application = internshipApplicationsEntity.findById(applicationId).orElse(null);
                    if(application != null){
                        InternshipEntity internship =internshipRepository.findById(application.getInternshipid()).orElse(null);
                        if(internship == null || !internship.getRecruiterUsername().equals(recruiterUsername)){
                            return new ResponseEntity<>(new ApiResponse(404,"Application not found"),HttpStatus.NOT_FOUND);
                        }
                        application.setStatus("rejected");
                        internshipApplicationsEntity.save(application);
                        return new ResponseEntity<>(new ApiResponse(200,
                        "Internship application rejected successfully"),HttpStatus.OK);
                    }else{
                    return new ResponseEntity<>(new ApiResponse(404,
                    "Application not found"),HttpStatus.NOT_FOUND);
                }
            }
        
        }
    



    