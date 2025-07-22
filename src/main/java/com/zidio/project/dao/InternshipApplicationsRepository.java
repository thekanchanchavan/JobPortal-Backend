package com.zidio.project.dao;

import org.springframework.data.repository.CrudRepository;

import com.zidio.project.entity.InternshipApplicationsEntity;

public interface InternshipApplicationsRepository extends CrudRepository<InternshipApplicationsEntity, Integer>{
    
}
