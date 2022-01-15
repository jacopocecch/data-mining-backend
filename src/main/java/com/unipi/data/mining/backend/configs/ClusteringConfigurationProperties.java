package com.unipi.data.mining.backend.configs;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
@ConfigurationProperties(prefix = "clustering")
public class ClusteringConfigurationProperties {

    @Value("${clustering.mode}")
    private String clusteringMode;

    @Value("${clustering.block-size-threshold}")
    private int blockSizeThreshold;

    @Value("${clustering.attribute-selection}")
    private boolean attributeSelection;

    @Value("${clustering.python.path}")
    private String pythonPath;

    public String getClusteringMode() {
        return clusteringMode;
    }

    public void setClusteringMode(String clusteringMode) {
        this.clusteringMode = clusteringMode;
    }

    public int getBlockSizeThreshold() {
        return blockSizeThreshold;
    }

    public void setBlockSizeThreshold(int blockSizeThreshold) {
        this.blockSizeThreshold = blockSizeThreshold;
    }

    public boolean isAttributeSelection() {
        return attributeSelection;
    }

    public void setAttributeSelection(boolean attributeSelection) {
        this.attributeSelection = attributeSelection;
    }

    public String getPythonPath() {
        return pythonPath;
    }

    public void setPythonPath(String pythonPath) {
        this.pythonPath = pythonPath;
    }
}
