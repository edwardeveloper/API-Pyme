package com.api.ntc6001.service;

import com.api.ntc6001.model.entity.Reporte;

import java.util.List;

public interface IReporte {

    Reporte save(Reporte reporte);

    Reporte findById(Long id);

    List<Reporte> findByMype(Long id);

}
