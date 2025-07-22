package com.zidio.project.dao;

import org.springframework.data.repository.CrudRepository;
import com.zidio.project.entity.StudentEntity;
public interface StudentRepository extends CrudRepository<StudentEntity, String> {

}
