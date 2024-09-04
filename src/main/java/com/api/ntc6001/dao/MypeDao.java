package com.api.ntc6001.dao;

import com.api.ntc6001.model.entity.Mype;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MypeDao extends CrudRepository<Mype, Long> {
    @Query(value =  "SELECT * FROM mype WHERE MCorreo LIKE :email",
            nativeQuery = true)
    Mype findByEmail(String email);
}
