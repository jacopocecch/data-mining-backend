package com.unipi.data.mining.backend.service;

import com.unipi.data.mining.backend.data.ClusterValues;
import com.unipi.data.mining.backend.entities.mongodb.Question;
import com.unipi.data.mining.backend.entities.mongodb.Survey;
import org.passay.CharacterData;
import org.passay.CharacterRule;
import org.passay.EnglishCharacterData;
import org.passay.PasswordGenerator;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import static org.passay.IllegalCharacterRule.ERROR_CODE;

@Service
public class Utils {


    public ClusterValues getClusterValues(Survey survey) {

        ClusterValues clusterValues = new ClusterValues();
        clusterValues.setAgreeableness(getQuestionsAverage(survey.getAgr()));
        clusterValues.setExtraversion(getQuestionsAverage(survey.getExt()));
        clusterValues.setOpenness(getQuestionsAverage(survey.getOpn()));
        clusterValues.setNeuroticism(getQuestionsAverage(survey.getEst()));
        clusterValues.setConscientiousness(getQuestionsAverage(survey.getCsn()));

        List<Question> questions = Stream.of(survey.getExt(), survey.getEst(), survey.getAgr(), survey.getOpn(), survey.getCsn())
                .flatMap(List::stream).toList();

        clusterValues.setTimeSpent(Math.round(questions.stream().mapToDouble(Question::getTime).sum() / 60 * 100.00) / 100.00);
        return clusterValues;
    }

    public ClusterValues getClusterValuesSelected(Survey survey, Map<String, List<String>> selectedAttributes) {
        ClusterValues clusterValues = new ClusterValues();

        clusterValues.setAgreeableness(getQuestionsAverage(survey.getSelectedAgr(selectedAttributes.get("AGR"))));
        clusterValues.setExtraversion(getQuestionsAverage(survey.getSelectedExt(selectedAttributes.get("EXT"))));
        clusterValues.setOpenness(getQuestionsAverage(survey.getSelectedOpn(selectedAttributes.get("OPN"))));
        clusterValues.setNeuroticism(getQuestionsAverage(survey.getSelectedEst(selectedAttributes.get("EST"))));
        clusterValues.setConscientiousness(getQuestionsAverage(survey.getSelectedCsn(selectedAttributes.get("CSN"))));

        List<Question> questions = Stream.of(survey.getSelectedExt(selectedAttributes.get("EXT")),
                        survey.getSelectedEst(selectedAttributes.get("EST")),
                        survey.getSelectedAgr(selectedAttributes.get("AGR")),
                        survey.getSelectedOpn(selectedAttributes.get("OPN")),
                        survey.getSelectedCsn(selectedAttributes.get("CSN")))
                .flatMap(List::stream).toList();

        clusterValues.setTimeSpent(Math.round(questions.stream().mapToDouble(Question::getTime).sum() / 60 * 100.00) / 100.00);
        return clusterValues;
    }

    public double getDistance(ClusterValues fromClusterValues, ClusterValues toClusterValues) {

        return Math.sqrt(Math.pow(fromClusterValues.getExtraversion() - toClusterValues.getExtraversion(), 2) +
                        Math.pow(fromClusterValues.getAgreeableness() - toClusterValues.getAgreeableness(), 2) +
                        Math.pow(fromClusterValues.getConscientiousness() - toClusterValues.getConscientiousness(), 2) +
                        Math.pow(fromClusterValues.getNeuroticism() - toClusterValues.getNeuroticism(), 2) +
                        Math.pow(fromClusterValues.getOpenness() - toClusterValues.getOpenness(), 2) +
                        Math.pow(fromClusterValues.getTimeSpent() - toClusterValues.getTimeSpent(), 2));
    }

    private double getQuestionsAverage(List<Question> questions) {
        return questions.stream().mapToDouble(Question::getValue).average().orElse(0.0);
    }

    public String generatePassword() {
        PasswordGenerator gen = new PasswordGenerator();
        CharacterData lowerCaseChars = EnglishCharacterData.LowerCase;
        CharacterRule lowerCaseRule = new CharacterRule(lowerCaseChars);
        lowerCaseRule.setNumberOfCharacters(2);

        CharacterData upperCaseChars = EnglishCharacterData.UpperCase;
        CharacterRule upperCaseRule = new CharacterRule(upperCaseChars);
        upperCaseRule.setNumberOfCharacters(2);

        CharacterData digitChars = EnglishCharacterData.Digit;
        CharacterRule digitRule = new CharacterRule(digitChars);
        digitRule.setNumberOfCharacters(2);

        CharacterData specialChars = new CharacterData() {

            public String getErrorCode() {
                return ERROR_CODE;
            }

            public String getCharacters() {
                return "!@#$%^&*()_+";
            }
        };
        CharacterRule splCharRule = new CharacterRule(specialChars);
        splCharRule.setNumberOfCharacters(2);

        return gen.generatePassword(10, splCharRule, lowerCaseRule,
                upperCaseRule, digitRule);
    }
}
