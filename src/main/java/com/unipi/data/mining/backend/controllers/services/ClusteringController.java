package com.unipi.data.mining.backend.controllers.services;

import com.unipi.data.mining.backend.configs.ClusteringConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@EnableConfigurationProperties(ClusteringConfigurationProperties.class)
@RestController
@RequestMapping("clustering")
public class ClusteringController extends ServiceController{

    final
    ClusteringConfigurationProperties clusteringConfigurationProperties;

    public ClusteringController(ClusteringConfigurationProperties clusteringConfigurationProperties) {
        this.clusteringConfigurationProperties = clusteringConfigurationProperties;
    }

    @EventListener(ContextRefreshedEvent.class)
    public void clustering(){

        if (Objects.equals(clusteringConfigurationProperties.getClusteringMode(), "standard")) {
            clustering.startClustering();
        } else {
            moaClustering.startClustering();
        }
    }
}
