package com.api.ntc6001.service;

import com.api.ntc6001.model.entity.Mype;

import java.util.List;

public interface IMype{
    Mype save(Mype pyme);

    Mype findById(Long id);

    Mype findByEmail(String email);

    void delete(Mype pyme);

    List<Mype> getMype();


}
