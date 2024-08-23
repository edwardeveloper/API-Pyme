package com.api.ntc6001.dao;

import com.api.ntc6001.model.entity.Pyme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PymeDao extends CrudRepository<Pyme, Long> {
    @Query(value =  "SELECT * FROM pyme WHERE PCorreo LIKE %:email",
            nativeQuery = true)
    Pyme findByEmail(String email);
}
