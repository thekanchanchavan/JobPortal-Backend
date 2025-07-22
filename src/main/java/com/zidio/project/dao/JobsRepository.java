package com.zidio.project.dao;

import org.springframework.data.repository.CrudRepository;
import com.zidio.project.entity.JobsEntity;
public interface JobsRepository extends CrudRepository<JobsEntity, Integer> {
    
}
