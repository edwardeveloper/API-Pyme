package com.api.ntc6001.service.impl;

import com.api.ntc6001.dao.MypeDao;
import com.api.ntc6001.model.entity.Mype;
import com.api.ntc6001.service.IMype;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class IMypeImpl implements IMype {

    @Autowired
    private MypeDao pymeDao;

    @Override
    public Mype save(Mype pyme) {
        return pymeDao.save(pyme);
    }

    @Override
    public Mype findById(Long id) {
        return pymeDao.findById(id).orElse(null);
    }

    @Override
    public Mype findByEmail(String email) {
        return pymeDao.findByEmail(email);
    }

    @Override
    public void delete(Mype pyme) {

    }

    @Override
    public List<Mype> getMype() {
        return null;
    }
}
