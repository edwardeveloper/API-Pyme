package com.api.ntc6001.service;

import com.api.ntc6001.model.entity.Pyme;

import java.util.List;

public interface IPyme{
    Pyme save(Pyme pyme);

    Pyme findById(Long id);

    Pyme findByEmail(String email);

    void delete(Pyme pyme);

    List<Pyme> getPyme();


}
