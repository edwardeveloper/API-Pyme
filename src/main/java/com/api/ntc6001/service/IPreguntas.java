package com.api.ntc6001.service;

import com.api.ntc6001.model.entity.Preguntas;

import java.util.List;

public interface IPreguntas{
    Preguntas save(Preguntas pregunta);

    Preguntas findById(Long id);

    void delete(Preguntas pregunta);

    List<Preguntas> getPregunta();
}
