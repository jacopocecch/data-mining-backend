package com.unipi.data.mining.backend.service.clustering;

import com.unipi.data.mining.backend.configs.ClusteringConfigurationProperties;
import com.unipi.data.mining.backend.entities.mongodb.MongoUser;
import com.unipi.data.mining.backend.entities.mongodb.Question;
import com.unipi.data.mining.backend.repositories.MongoUserRepository;
import com.unipi.data.mining.backend.service.Utils;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.*;
import java.util.*;


abstract class ClusteringService {

    @Autowired
    protected MongoUserRepository mongoUserRepository;

    @Autowired
    protected Utils utils;

    @Autowired
    protected ClusteringConfigurationProperties clusteringConfigurationProperties;

    private Map<String, List<String>> selectedAttributes;

    public Map<String, List<String>> getSelectedAttributes() {
        return selectedAttributes;
    }

    public void setSelectedAttributes(List<MongoUser> users) {

        List<String> rows = new ArrayList<>();
        for (MongoUser user: users) {
            StringBuilder stringBuilder = new StringBuilder();
            if (user.getSurvey() == null) continue;
            for (Question question: user.getSurvey().getExt()){
                stringBuilder.append(question.getValue());
                stringBuilder.append(",");
            }
            for (Question question: user.getSurvey().getEst()){
                stringBuilder.append(question.getValue());
                stringBuilder.append(",");
            }
            for (Question question: user.getSurvey().getAgr()){
                stringBuilder.append(question.getValue());
                stringBuilder.append(",");
            }
            for (Question question: user.getSurvey().getCsn()){
                stringBuilder.append(question.getValue());
                stringBuilder.append(",");
            }
            for (Question question: user.getSurvey().getOpn()){
                stringBuilder.append(question.getValue());
                if (!Objects.equals(question.getName(), "OPN10")) stringBuilder.append(",");
            }
            rows.add(stringBuilder.toString());
        }
        String arg = String.join("\n", rows);
        System.out.println("Performing Attribute Selection");
        PrintWriter writer;
        try {
            writer = new PrintWriter("src/main/resources/arg.txt");
            writer.print(arg);
            writer.close();
        } catch (FileNotFoundException e){
            e.printStackTrace();
        }
        Process p = null;
        System.out.println("Launching python script");
        try {
            p = new ProcessBuilder(clusteringConfigurationProperties.getPythonPath(), "src/main/resources/script.py").start();
            p.waitFor();
        } catch (Exception e) {
            e.printStackTrace();
        }
        assert p != null;
        BufferedReader bfr = new BufferedReader(new InputStreamReader(p.getInputStream()));
        Map<String, List<String>> questionsMap = new HashMap<>();
        List<String> attributeList = Arrays.asList("EXT", "EST", "AGR", "CSN", "OPN");
        String line;
        int i = 0;
        try {
            while ((line = bfr.readLine()) != null){
                line = line.replaceAll("'", "");
                line = line.replaceAll("\\[", "");
                line = line.replaceAll("]", "");
                String[] attributes = line.split(",");
                questionsMap.put(attributeList.get(i), Arrays.stream(attributes).toList());
                i++;
            }
        } catch(Exception e){
            e.printStackTrace();
        }
        this.selectedAttributes = questionsMap;
    }

    boolean isAttributeSelectionCorrect() {

        boolean correctness = selectedAttributes.size() == 5;

        for (Map.Entry<String, List<String>> entry: selectedAttributes.entrySet()){
            if (entry.getValue().size() != 5) {
                correctness = false;
                break;
            }
        }
        return correctness;
    }
}
