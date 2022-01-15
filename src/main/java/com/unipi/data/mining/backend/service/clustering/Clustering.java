package com.unipi.data.mining.backend.service.clustering;

import com.unipi.data.mining.backend.data.ClusterValues;
import com.unipi.data.mining.backend.entities.mongodb.MongoUser;
import org.springframework.stereotype.Service;
import weka.clusterers.FilteredClusterer;
import weka.clusterers.SimpleKMeans;
import weka.core.*;
import weka.filters.unsupervised.attribute.Remove;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
public class
Clustering extends ClusteringService {

    private final ArrayList<Attribute> attributes = new ArrayList<>();
    private Instances dataset;
    // attribute used to avoid to scan again the dataset
    private Instances datasetUpdated;
    /* using the FilteredClusterer that allows to save the id of the user
    as attribute of the instance without considering it in the KMeans algorithm */
    private FilteredClusterer filteredClusterer;
    // attribute that indicates how many instances to wait before re-executing clustering (block processing)
    private int blockSize;

    public Clustering() {
        attributes.add(new Attribute("id",(ArrayList<String>)null));
        attributes.add(new Attribute("neurotic"));
        attributes.add(new Attribute("agreeable"));
        attributes.add(new Attribute("openness"));
        attributes.add(new Attribute("extraversion"));
        attributes.add(new Attribute("conscientiousness"));
        attributes.add(new Attribute("timeSpent"));
    }

    public void startClustering() {
        System.out.println("Initializing the KMeans Clustering Algorithm....");
        List<MongoUser> mongoUsers = mongoUserRepository.findAll();
        if (clusteringConfigurationProperties.isAttributeSelection()) setSelectedAttributes(mongoUsers);
        Map<String, MongoUser> mongoUserMap = mongoUsers.stream().collect(Collectors.toMap(mongoUser -> mongoUser.getId().toString(), Function.identity()));
        int numInstances = mongoUserMap.size();
        dataset = new Instances("PersonalityDataset", attributes, numInstances);
        double[] values;
        for (Map.Entry<String, MongoUser> mongoUserEntry: mongoUserMap.entrySet()) {
            values = getValues(mongoUserEntry.getValue());
            Instance inst = new DenseInstance(1, values);
            dataset.add(inst);
        }
        datasetUpdated = dataset;
        buildClusterer();
        updateClusters(mongoUserMap);
    }

    private void buildClusterer() {
        filteredClusterer = new FilteredClusterer();
        try {
            SimpleKMeans clusterer = new SimpleKMeans();
            clusterer.setNumClusters(5);
            clusterer.setDistanceFunction(new EuclideanDistance());
            filteredClusterer.setClusterer(clusterer);
            Remove filter = new Remove();
            // not considering the id as a feature
            filter.setAttributeIndices("first");
            filteredClusterer.setFilter(filter);
            if(dataset.size() != 0)
                filteredClusterer.buildClusterer(dataset);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /* training the clusterer with the instances on the database
     and updating the field in the database if changed */
    private void updateClusters(Map<String, MongoUser> mongoUserMap) {
        Enumeration<Instance> instances = dataset.enumerateInstances();
        List<MongoUser> usersToBeUpdated = new ArrayList<>();
        while (instances.hasMoreElements()) {
            try {
                Instance instance = instances.nextElement();
                int cluster = filteredClusterer.clusterInstance(instance) + 1;
                String userId = instance.toString(0);
                MongoUser user = mongoUserMap.get(userId);
                if (user.getCluster() != cluster) {
                    user.setCluster(cluster);
                    usersToBeUpdated.add(user);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("Users to be updated: " + usersToBeUpdated.size() + " / " + dataset.size());
        mongoUserRepository.saveAll(usersToBeUpdated);
        System.out.println("Users' cluster updated!");
    }

    // function called after registration to perform clustering on the new instance
    public void performClustering(MongoUser user){
        System.out.println("Performing clustering on the new instance..");
        try {
            double[] values;
            values = getValues(user);
            Instance instance = new DenseInstance(1,values);
            instance.setDataset(dataset);
            int cluster = filteredClusterer.clusterInstance(instance) + 1;
            user.setCluster(cluster);
            System.out.println("Cluster assigned: " + cluster);
            blockSize++;
            datasetUpdated.add(instance);
            // if blockSizeThreshold is reached, we rebuild the clusterer and update the cluster ID in the DB
            if(blockSize == clusteringConfigurationProperties.getBlockSizeThreshold()){
                System.out.println("blockSizeThreshold reached, re updating clusters...");
                dataset = datasetUpdated;
                blockSize = 0;
                buildClusterer();
                Map<String, MongoUser> mongoUserMap = mongoUserRepository.findAll().stream().collect(Collectors.toMap(mongoUser -> mongoUser.getId().toString(), Function.identity()));
                updateClusters(mongoUserMap);
            }
        } catch(Exception e){
            e.printStackTrace();
        }
    }

    // function to determine the clusters' average values
    private double[] getValues(MongoUser mongoUser) {
        double[] values = new double[dataset.numAttributes()];
        ClusterValues clusterValues;
        if (clusteringConfigurationProperties.isAttributeSelection() && isAttributeSelectionCorrect()) {
            clusterValues = utils.getClusterValuesSelected(mongoUser.getSurvey(), getSelectedAttributes());
        } else {
            clusterValues = utils.getClusterValues(mongoUser.getSurvey());
        }
        values[0] = dataset.attribute("id").addStringValue(mongoUser.getId().toString());
        values[1] = clusterValues.getNeuroticism();
        values[2] = clusterValues.getAgreeableness();
        values[3] = clusterValues.getOpenness();
        values[4] = clusterValues.getExtraversion();
        values[5] = clusterValues.getConscientiousness();
        values[6] = clusterValues.getTimeSpent();
        return values;
    }
}
