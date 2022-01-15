package com.unipi.data.mining.backend.controllers.services;

import com.unipi.data.mining.backend.dtos.Mapper;
import com.unipi.data.mining.backend.service.clustering.MOAClustering;
import com.unipi.data.mining.backend.service.clustering.Clustering;
import com.unipi.data.mining.backend.service.db.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

@Validated
@Transactional(readOnly = true)
abstract class ServiceController {

    @Autowired
    protected UserService userService;

    @Autowired
    protected MOAClustering moaClustering;

    @Autowired
    protected Clustering clustering;

    @Autowired
    protected Mapper mapper;
}
