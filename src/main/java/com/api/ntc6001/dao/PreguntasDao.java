package com.api.ntc6001.dao;

import com.api.ntc6001.model.entity.Preguntas;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PreguntasDao extends CrudRepository<Preguntas, Long> {
    @Query(value =  "SELECT * FROM pregunta WHERE PCapitulo LIKE :pcapitulo",
            nativeQuery = true)
    List<Preguntas> findByCapitulo(String pcapitulo);
}
