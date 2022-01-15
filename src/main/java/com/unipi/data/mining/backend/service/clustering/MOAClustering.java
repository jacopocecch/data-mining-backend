package com.unipi.data.mining.backend.service.clustering;
import com.unipi.data.mining.backend.data.ClusterValues;
import com.unipi.data.mining.backend.entities.mongodb.MongoUser;
import com.yahoo.labs.samoa.instances.*;
import moa.cluster.Cluster;
import moa.cluster.Clustering;
import moa.clusterers.clustream.WithKmeans;
import moa.core.AutoExpandVector;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MOAClustering extends ClusteringService{

    private WithKmeans clusterer;

    public void startClustering() {
        System.out.println("Starting MOA Clustering Algorithm...");
        clusterer = new WithKmeans();
        // setting parameters
        clusterer.kOption.setValue(5);
        clusterer.kernelRadiFactorOption.setValue(2);
        clusterer.maxNumKernelsOption.setValue(100);
        clusterer.timeWindowOption.setValue(1000);
        clusterer.resetLearning();
        trainClusterer();
        Clustering result = clusterer.getClusteringResult();
        System.out.println("Number of clusters: " + result.size());
        AutoExpandVector<Cluster> clusters = result.getClustering();
        for(int j = 0; j < result.size(); j++) {
            System.out.print("Cluster " + j + " has weight " + clusters.get(j).getWeight() + " and center (");
            for(int k = 0; k < result.dimension(); ++k) {
                System.out.print(clusters.get(j).getCenter()[k]);
                if(k != result.dimension()-1)
                    System.out.print(", ");
                else System.out.print(")\n");
            }
        }
    }

    /* training the clusterer with the instances on the database
     and updating the field in the database if changed */
    private void trainClusterer() {
        List<MongoUser> users = mongoUserRepository.findAll();
        List<MongoUser> usersToBeUpdated = new ArrayList<>();
        if (clusteringConfigurationProperties.isAttributeSelection()) setSelectedAttributes(users);
        double[] values;
        int cluster;
        for (MongoUser user : users) {
            if (user.getSurvey() != null) {
                try {
                    values = getValues(user);
                    Instance unlabeled = new DenseInstance(6, values);
                    clusterer.trainOnInstance(unlabeled);
                    cluster = getClusterID(unlabeled);
                    if (user.getCluster() != cluster) {
                        user.setCluster(cluster);
                        usersToBeUpdated.add(user);
                    }
                } catch(Exception e){
                    e.printStackTrace();
                }
            }
        }
        System.out.println("Users to be updated: " + usersToBeUpdated.size() + " / " + users.size());
        mongoUserRepository.saveAll(usersToBeUpdated);
        System.out.println("Users' cluster updated!");
    }

    // function called after registration to perform clustering on the new instance
    public void performClustering(MongoUser user){
        System.out.println("Performing clustering on the new instance..");
        double[] values;
        int cluster;
        try {
            values = getValues(user);
            Instance unlabeled = new DenseInstance(6, values);
            clusterer.trainOnInstance(unlabeled);
            cluster = getClusterID(unlabeled);
            System.out.println("Cluster assigned: " + cluster);
            user.setCluster(cluster);
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // function called after login to determine if the cluster has changed, while the stream of data arrived
    public int getLoginCluster(MongoUser user){
        double[] values;
        try {
            values = getValues(user);
            Instance unlabeled = new DenseInstance(6, values);
            return getClusterID(unlabeled);
        } catch(Exception e){
            e.printStackTrace();
            return 0;
        }
    }

    // functions to determine the closest cluster
    private int getClusterID(Instance unlabeled){
        int clusterID = 0;
        Clustering result = clusterer.getClusteringResult();
        AutoExpandVector<Cluster> clusters = result.getClustering();
        double max = 0;
        double distance;
        for(int j = 0; j < clusters.size(); ++j){
            distance = getClusterDistance(clusters.get(j).getCenter(),unlabeled);
            if(distance > max){
                max = distance;
                clusterID = j + 1;
            }
        }
        //cluster 1: default for the first 100 instances
        if(clusterID == 0)
            return clusterID + 1;
        return clusterID;
    }

    public double getClusterDistance(double[] center, Instance instance){
        double sum = 0;
        for(int i = 0; i < center.length; ++i) {
            sum = (sum + ((instance.value(i) - center[i])*(instance.value(i) - center[i])));
        }
        return Math.sqrt(sum);
    }

    // function to determine the clusters' average values
    private double[] getValues(MongoUser mongoUser) {
        int numAttributes = 6;
        double[] values = new double[numAttributes];
        ClusterValues clusterValues;
        if (clusteringConfigurationProperties.isAttributeSelection() && isAttributeSelectionCorrect()) {
            clusterValues = utils.getClusterValuesSelected(mongoUser.getSurvey(), getSelectedAttributes());
        } else {
            clusterValues = utils.getClusterValues(mongoUser.getSurvey());
        }
        values[0] = clusterValues.getNeuroticism();
        values[1] = clusterValues.getAgreeableness();
        values[2] = clusterValues.getOpenness();
        values[3] = clusterValues.getExtraversion();
        values[4] = clusterValues.getConscientiousness();
        values[5] = clusterValues.getTimeSpent();
        return values;
    }
}

