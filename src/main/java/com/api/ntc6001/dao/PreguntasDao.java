package com.api.ntc6001.dao;

import com.api.ntc6001.model.entity.Preguntas;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PreguntasDao extends CrudRepository<Preguntas, Long> {
}
