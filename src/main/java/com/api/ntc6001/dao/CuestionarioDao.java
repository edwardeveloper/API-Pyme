package com.api.ntc6001.dao;

import com.api.ntc6001.model.dto.CuestionarioInformeTotalDto;
import com.api.ntc6001.model.entity.Cuestionario;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CuestionarioDao extends CrudRepository<Cuestionario, Integer> {

    @Query(value =  "SELECT * FROM cuestionario WHERE mype_idmype LIKE :pyme AND pregunta_idpregunta LIKE :pregunta",
            nativeQuery = true)
    Cuestionario findByPreguntaPyme(Integer pyme, Integer pregunta);

    @Query(value =  "SELECT * FROM cuestionario WHERE mype_idmype LIKE :mype ORDER BY pregunta_idpregunta ASC",
            nativeQuery = true)
    List<Cuestionario> findByPreguntaId(String mype);

    @Query(value =  "SELECT p.PCapitulo as capitulo, COUNT(p.PPregunta) as countpregunta, c.mype_idmype as idmype, COUNT(c.pregunta_idpregunta) as countcuestionario FROM pregunta as p LEFT JOIN cuestionario as c ON c.mype_idmype=:mype AND c.pregunta_idpregunta=p.idpregunta GROUP BY p.pcapitulo",
            nativeQuery = true)
    List<?> findByCapituloPyme(int mype);

    @Query(value =  "SELECT p.PCapitulo as capitulo, COUNT(p.PPregunta) as countPregunta, COUNT(c.pregunta_idpregunta) as countCuestionario, SUM(CASE WHEN c.PPRespuestas = 'cumple' THEN 1 ELSE 0 END) AS cumple, SUM(CASE WHEN c.PPRespuestas = 'cumple parcialmente' THEN 1 ELSE 0 END) AS cumpleParcialmente,SUM(CASE WHEN c.PPRespuestas = 'no cumple' THEN 1 ELSE 0 END) AS noCumple FROM pregunta as p LEFT JOIN cuestionario as c ON c.pregunta_idpregunta=p.idpregunta AND c.mype_idmype= :mype GROUP BY p.PCapitulo",
            nativeQuery = true)
    List<?> findByPymeReporte(int mype);
}
