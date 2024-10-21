package com.api.ntc6001.service;

import com.api.ntc6001.model.dto.CuestionarioInformeTotalDto;
import com.api.ntc6001.model.entity.Cuestionario;

import java.util.List;

public interface ICuestionario{

    Cuestionario save(Cuestionario cuestionario);

    Cuestionario findById(Integer id);
    List<Cuestionario> findByPregunta(String pregunta);
    Cuestionario findByIdPreguntaPyme(Integer pyme, Integer pregunta);
    List<?> findByIdPreguntaPyme(Integer pyme);
    List<?> findByIdPymeReporte(Integer pyme);
    List<?> findSeccionReporte(Integer pyme);
    List<?> findSeccionItemReporte(Integer pyme);

    void delete(Cuestionario cuestionario);

    List<Cuestionario> getCuestionario();

}
