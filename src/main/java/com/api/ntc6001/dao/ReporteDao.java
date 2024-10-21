package com.api.ntc6001.dao;

import com.api.ntc6001.model.entity.Reporte;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReporteDao extends CrudRepository<Reporte, Long> {

    @Query(value =  "SELECT * FROM reporte WHERE mype_idmype LIKE :mype",
            nativeQuery = true)
    List<Reporte> findByMype(Long mype);


    @Query(value =  "SELECT * FROM reporte WHERE mype_idmype=:mype AND RSeccion=:seccion ORDER by idreporte DESC limit 1",
            nativeQuery = true)
    Reporte findByMypeSeccionSingle(Long mype, String seccion);

}
