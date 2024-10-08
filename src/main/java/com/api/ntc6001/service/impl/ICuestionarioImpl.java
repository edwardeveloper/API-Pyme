package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.CuestionarioDao;
import com.api.ntc6001.model.entity.Cuestionario;
import com.api.ntc6001.service.ICuestionario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
public class ICuestionarioImpl implements ICuestionario {

    @Autowired
    private CuestionarioDao cuestionarioDao;

//    public ICuestionarioImpl(CuestionarioDao cuestionarioDao) {
//        this.cuestionarioDao = cuestionarioDao;
//    }

    @Override
    @Transactional
    public Cuestionario save(Cuestionario cuestionario) {
        return cuestionarioDao.save(cuestionario);
    }

    @Override
    public Cuestionario findById(Integer id) {
        return cuestionarioDao.findById(id).orElse(null);
    }

    @Override
    public Cuestionario findByIdPregunta(Integer pyme, Integer pregunta) {
        return cuestionarioDao.findByPregunta(pyme, pregunta);
    }

    @Override
    public void delete(Cuestionario cuestionario) {

    }

    @Override
    public List<Cuestionario> getCuestionario() {
        return null;
//        return cuestionarioDao.findAll();
    }
}
