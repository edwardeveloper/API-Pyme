package com.api.ntc6001.dao;

import com.api.ntc6001.model.entity.Cuestionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CuestionarioDao extends CrudRepository<Cuestionario, Integer> {

    @Query(value =  "SELECT * FROM cuestionario WHERE pyme_idpyme LIKE %:pyme AND pregunta_idpregunta LIKE %:pregunta",
            nativeQuery = true)
    Cuestionario findByPregunta(Integer pyme, Integer pregunta);
}
