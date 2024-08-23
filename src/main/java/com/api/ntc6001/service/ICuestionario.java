package com.api.ntc6001.service;

import com.api.ntc6001.model.entity.Cuestionario;

import java.util.List;

public interface ICuestionario{
    Cuestionario save(Cuestionario cuestionario);

    Cuestionario findById(Integer id);
    Cuestionario findByIdPregunta(Integer pyme, Integer pregunta);

    void delete(Cuestionario cuestionario);

    List<Cuestionario> getCuestionario();
}
