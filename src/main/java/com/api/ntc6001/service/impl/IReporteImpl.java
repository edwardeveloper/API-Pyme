package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.ReporteDao;
import com.api.ntc6001.model.entity.Reporte;
import com.api.ntc6001.service.IReporte;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IReporteImpl implements IReporte {

    @Autowired
    private ReporteDao reporteDao;

    public IReporteImpl(ReporteDao reporteDao) {
        this.reporteDao = reporteDao;
    }

    @Override
    public Reporte save(Reporte reporte) {
        return reporteDao.save(reporte);
    }

    @Override
    public Reporte findById(Long id) {
        return reporteDao.findById(id).orElse(null);
    }

    @Override
    public List<Reporte> findByMype(Long id) {
        return reporteDao.findByMype(id);
    }
}
