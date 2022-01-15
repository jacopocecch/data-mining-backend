package com.unipi.data.mining.backend.entities.mongodb;

import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.ArrayList;
import java.util.List;

@Document
public class Survey {

    @Field(name = "EXT")
    private ArrayList<Question> ext;
    @Field(name = "EST")
    private ArrayList<Question> est;
    @Field(name = "AGR")
    private ArrayList<Question> agr;
    @Field(name = "CSN")
    private ArrayList<Question> csn;
    @Field(name = "OPN")
    private ArrayList<Question> opn;

    public Survey() {
    }

    public Survey(ArrayList<Question> ext, ArrayList<Question> est, ArrayList<Question> agr, ArrayList<Question> csn, ArrayList<Question> opn) {
        this.ext = ext;
        this.est = est;
        this.agr = agr;
        this.csn = csn;
        this.opn = opn;
    }

    public ArrayList<Question> getExt() {
        return ext;
    }

    public void setExt(ArrayList<Question> ext) {
        this.ext = ext;
    }

    public ArrayList<Question> getEst() {
        return est;
    }

    public void setEst(ArrayList<Question> est) {
        this.est = est;
    }

    public ArrayList<Question> getAgr() {
        return agr;
    }

    public void setAgr(ArrayList<Question> agr) {
        this.agr = agr;
    }

    public ArrayList<Question> getCsn() {
        return csn;
    }

    public void setCsn(ArrayList<Question> csn) {
        this.csn = csn;
    }

    public ArrayList<Question> getOpn() {
        return opn;
    }

    public void setOpn(ArrayList<Question> opn) {
        this.opn = opn;
    }

    public List<Question> getSelectedExt(List<String> selectedAttributes) {
        return ext.stream().filter(question -> selectedAttributes.contains(question.getName())).toList();
    }

    public List<Question> getSelectedEst(List<String> selectedAttributes) {
        return est.stream().filter(question -> selectedAttributes.contains(question.getName())).toList();
    }

    public List<Question> getSelectedAgr(List<String> selectedAttributes) {
        return agr.stream().filter(question -> selectedAttributes.contains(question.getName())).toList();
    }

    public List<Question> getSelectedOpn(List<String> selectedAttributes) {
        return opn.stream().filter(question -> selectedAttributes.contains(question.getName())).toList();
    }

    public List<Question> getSelectedCsn(List<String> selectedAttributes) {
        return csn.stream().filter(question -> selectedAttributes.contains(question.getName())).toList();
    }

    @Override
    public String toString() {
        return "Survey{" +
                "ext=" + ext +
                ", est=" + est +
                ", agr=" + agr +
                ", csn=" + csn +
                ", opn=" + opn +
                '}';
    }
}
