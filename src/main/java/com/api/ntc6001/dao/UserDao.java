package com.api.ntc6001.dao;

import com.api.ntc6001.model.entity.Users;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao extends CrudRepository<Users, Long> {
    @Query(value =  "SELECT * FROM user WHERE UCorreo LIKE %:email",
            nativeQuery = true)
    Users findByEmail(String email);
}
