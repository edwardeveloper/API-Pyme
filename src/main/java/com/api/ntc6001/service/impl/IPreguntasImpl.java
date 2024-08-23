package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.PreguntasDao;
import com.api.ntc6001.model.entity.Preguntas;
import com.api.ntc6001.service.IPreguntas;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Transactional
public class IPreguntasImpl implements IPreguntas {

    @Autowired
    private PreguntasDao preguntasDao;

    public IPreguntasImpl(PreguntasDao preguntasDao) {
        this.preguntasDao = preguntasDao;
    }

    @Override
    public Preguntas save(Preguntas pregunta) {
        return null;
    }

    @Override
    public Preguntas findById(Long id) {
        return preguntasDao.findById(id).orElse(null);
    }

    @Override
    public void delete(Preguntas pregunta) {

    }

    @Override
    public List<Preguntas> getPregunta() {
        return null;
    }
}
