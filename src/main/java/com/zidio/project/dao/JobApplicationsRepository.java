package com.zidio.project.dao;

import org.springframework.data.repository.CrudRepository;
import com.zidio.project.entity.JobApplicationsEntity;
public interface JobApplicationsRepository extends CrudRepository<JobApplicationsEntity, Integer> {
    
}
