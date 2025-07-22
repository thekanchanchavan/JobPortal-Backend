package com.zidio.project.dao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.zidio.project.entity.RecruiterEntity;
import com.zidio.project.model.Jobs;
import com.zidio.project.model.Internship;
import com.zidio.project.model.Recruiter;
import com.zidio.project.api.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.zidio.project.entity.JobsEntity;
import com.zidio.project.entity.InternshipEntity;
@Repository(value="RecruiterDAO")
public class RecruiterDAOImpl {
    @Autowired
    private CrudRepository<RecruiterEntity, String>recruiterRepository;
    @Autowired
    private CrudRepository<JobsEntity, Integer> jobsRepository;
    @Autowired
    private CrudRepository<InternshipEntity, Integer> internshipRepository;

    public ResponseEntity<Recruiter> getMyProfile(String username) throws Exception{
        RecruiterEntity recruiterEntity = recruiterRepository.findById(username).orElse(null);
        if(recruiterEntity != null){
            Recruiter recruiter = new Recruiter();
            recruiter.setUsername(recruiterEntity.getUsername());
            recruiter.setName(recruiterEntity.getName());
            recruiter.setMobilenumber(recruiterEntity.getMobilenumber());
            recruiter.setCompanyname(recruiterEntity.getCompanyname());
            recruiter.setLocation(recruiterEntity.getLocation());
            recruiter.setImage(recruiterEntity.getImage());
            return new ResponseEntity<>(recruiter, HttpStatus.OK);
        }else{
            Recruiter recruiter = new Recruiter();
            return new ResponseEntity<>(recruiter, HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<ApiResponse> updateProfile(Recruiter recruiter)throws Exception{
        System.out.println("username:" + recruiter.getUsername());
        RecruiterEntity existingRecruiter = recruiterRepository.findById(recruiter.getUsername()).orElse(null);
        if(existingRecruiter != null){
            RecruiterEntity recruiterEntity = new RecruiterEntity();
            recruiterEntity.setUsername(recruiter.getUsername());
            recruiterEntity.setName(recruiter.getName());
            recruiterEntity.setMobilenumber(recruiter.getMobilenumber());
            recruiterEntity.setLocation(recruiter.getLocation());
            recruiterEntity.setImage(recruiter.getImage());
            recruiterEntity.setCompanyname(recruiter.getCompanyname());
            recruiterRepository.save(recruiterEntity);
            return new ResponseEntity<>(new ApiResponse(200,
            "Profile Updated Successfully"),HttpStatus.OK);

        }else{
            System.out.println("Recruiter dones not exist" + recruiter.getUsername());
            return new ResponseEntity<>(new ApiResponse(404,
            "Recruiter dones not exist"), HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<ApiResponse> addJob(Jobs job)throws Exception{
        JobsEntity jobsEntity = new JobsEntity();
        jobsEntity.setJobTitle(job.getJobTitle());
        jobsEntity.setJobDescription(job.getJobDescription());
        jobsEntity.setCompanyName(job.getCompanyName());
        jobsEntity.setLocation(job.getLocation());
        jobsEntity.setCompanyWebsite(job.getCompanyWebsite());
        jobsEntity.setSalaryRange(job.getSalaryRange());
        jobsEntity.setJobType(job.getJobType());
        jobsEntity.setExperienceRequired(job.getExperienceRequired());
        jobsEntity.setSkillsRequired(job.getSkillsRequired());
        jobsEntity.setDatePosted(job.getDatePosted());
        jobsEntity.setApplicationDeadline(job.getApplicationDeadline());
        jobsEntity.setRecruiterUsername(job.getRecruiterUsername());
        jobsEntity.setEducationRequired(job.getEducationRequired());
        JobsEntity savedJob = jobsRepository.save(jobsEntity);
        Integer jobId = savedJob.getJobId();
        return new ResponseEntity<>(new ApiResponse(201,
        "Job Added with job id:"+ jobId), HttpStatus.OK);
    }
    public ResponseEntity<ApiResponse> updateJob(Jobs job) throws Exception{
        JobsEntity existingJob = jobsRepository.findById(job.getJobId()).orElse(null);
        if(existingJob != null && existingJob.getRecruiterUsername().equals(job.getRecruiterUsername())){
            JobsEntity jobEntity = new JobsEntity();
            jobEntity.setJobId(job.getJobId());
            jobEntity.setJobTitle(job.getJobTitle());
            jobEntity.setJobDescription(job.getJobDescription());
            jobEntity.setCompanyName(job.getCompanyName());
            jobEntity.setLocation(job.getLocation());
            jobEntity.setCompanyWebsite(job.getCompanyWebsite());
            jobEntity.setSalaryRange(job.getSalaryRange());
            jobEntity.setJobType(job.getJobType());
            jobEntity.setExperienceRequired(job.getExperienceRequired());
            jobEntity.setSkillsRequired(job.getSkillsRequired());
            jobEntity.setDatePosted(job.getDatePosted());
            jobEntity.setApplicationDeadline(job.getApplicationDeadline());
            jobEntity.setRecruiterUsername(job.getRecruiterUsername());
            jobEntity.setEducationRequired(job.getEducationRequired());
            JobsEntity savedJob = jobsRepository.save(jobEntity);
            Integer jobId= savedJob.getJobId();
            return new ResponseEntity<>(new ApiResponse(200,
            "Job updated for id:"+ jobId),HttpStatus.OK);
        }else{
            return new ResponseEntity<>(new ApiResponse(404,
            "Job does not exist"),HttpStatus.NOT_FOUND);
        }
    }
    public ResponseEntity<ApiResponse> addInternship(Internship internship) throws Exception{
        InternshipEntity internshipEntity = new InternshipEntity();
        internshipEntity.setInternshipTitle(internship.getInternshipTitle());
        internshipEntity.setInternshipDescription(internship.getInternshipDescription());
        internshipEntity.setCompanyName(internship.getCompanyName());
        internshipEntity.setLocation(internship.getLocation());
        internshipEntity.setCompanyWebsite(internship.getCompanyWebsite());
        internshipEntity.setStipend(internship.getStipend());
        internshipEntity.setSkillsRequired(internship.getSkillsRequired());
        internshipEntity.setDatePosted(internship.getDatePosted());
        internshipEntity.setApplicationDeadline(internship.getApplicationDeadline());
        internshipEntity.setRecruiterUsername(internship.getRecruiterUsername());
        internshipEntity.setEducationRequired(internship.getEducationRequired());
        internshipEntity.setStartDate(internship.getStartDate());
        internshipEntity.setEndDate(internship.getEndDate());
        InternshipEntity savedInternship = internshipRepository.save(internshipEntity);
        Integer internshipId= savedInternship.getInternshipId();
        return new ResponseEntity<>(new ApiResponse(201,
        "Internship Added with Internship Id:"+ internshipId),HttpStatus.OK);
    }
    public ResponseEntity<ApiResponse> updateInternship(Internship internship)throws Exception{
        InternshipEntity existingInternship = internshipRepository.findById(internship.getInternshipId()).orElse(null);
        if(existingInternship != null&& existingInternship.getRecruiterUsername().equals(internship.getRecruiterUsername())){
        InternshipEntity internshipEntity = new InternshipEntity();
        internshipEntity.setInternshipId(internship.getInternshipId());
        internshipEntity.setInternshipTitle((internship.getInternshipTitle()));
        internshipEntity.setInternshipDescription(internship.getInternshipDescription());
        internshipEntity.setCompanyName(internship.getCompanyName());
        internshipEntity.setCompanyWebsite(internship.getCompanyWebsite());
        internshipEntity.setLocation(internship.getLocation());
        internshipEntity.setStipend(internship.getStipend());
        internshipEntity.setSkillsRequired(internship.getSkillsRequired());
        internshipEntity.setDatePosted(internship.getDatePosted());
        internshipEntity.setApplicationDeadline(internship.getApplicationDeadline());
        internshipEntity.setRecruiterUsername(internship.getRecruiterUsername());
        internshipEntity.setEducationRequired(internship.getEducationRequired());
        internshipEntity.setStartDate(internship.getStartDate());
        internshipEntity.setEndDate(internship.getEndDate());
        InternshipEntity savedInternship = internshipRepository.save(internshipEntity);
        Integer internshipId = savedInternship.getInternshipId();
        return new ResponseEntity<>(new ApiResponse(200,
        "Internship updated for internship id:"+internshipId),HttpStatus.OK);
    }else{
        return new ResponseEntity<>(new ApiResponse(404,
        "Internship does not exist"),HttpStatus.NOT_FOUND);
    }
}
public ResponseEntity<ApiResponse> deleteInternship(Integer internshipId,String recruiterUsername)throws Exception{
    InternshipEntity existingInternship = internshipRepository.findById(internshipId).orElse(null);
    if(existingInternship != null && existingInternship.getRecruiterUsername().equals(recruiterUsername)){
    internshipRepository.delete(existingInternship);
    return new ResponseEntity<>(new ApiResponse(200,
    "Internship deleted for internship id: " + internshipId),HttpStatus.OK);
    }else{
    return new ResponseEntity<>(new ApiResponse(404,
    "Internship does not exist"),HttpStatus.NOT_FOUND);

    }
}

public ResponseEntity<ApiResponse> deleteJob(Integer jobId, String recruiterUsername) throws Exception{
  JobsEntity existingJob = jobsRepository.findById(jobId).orElse(null);
  if(existingJob != null && existingJob.getRecruiterUsername().equals(recruiterUsername)){
  jobsRepository.delete(existingJob);
  return new ResponseEntity<>(new ApiResponse(200,
  "Job deleted for jobid: " + jobId),HttpStatus.OK);
  } else {
    return new ResponseEntity<>(new ApiResponse(404,
  "Job does not exist"),HttpStatus.NOT_FOUND);
  }
    }
}