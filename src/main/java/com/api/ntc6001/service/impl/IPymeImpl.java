package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.PymeDao;
import com.api.ntc6001.model.entity.Pyme;
import com.api.ntc6001.service.IPyme;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IPymeImpl implements IPyme {

    @Autowired
    private PymeDao pymeDao;

    @Override
    public Pyme save(Pyme pyme) {
        return pymeDao.save(pyme);
    }

    @Override
    public Pyme findById(Long id) {
        return pymeDao.findById(id).orElse(null);
    }

    @Override
    public Pyme findByEmail(String email) {
        return pymeDao.findByEmail(email);
    }

    @Override
    public void delete(Pyme pyme) {

    }

    @Override
    public List<Pyme> getPyme() {
        return null;
    }
}
