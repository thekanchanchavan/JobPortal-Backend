package com.zidio.project.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.zidio.project.entity.UserEntity;
@Repository
public interface UserRepository extends CrudRepository<UserEntity, String>
 {
    
}
