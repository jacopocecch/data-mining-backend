package com.unipi.data.mining.backend.dtos;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.io.Serializable;
import java.util.ArrayList;

public class SurveyDto implements Serializable {

    @JsonProperty(value = "EXT")
    private ArrayList<QuestionDto> ext;
    @JsonProperty(value = "EST")
    private ArrayList<QuestionDto> est;
    @JsonProperty(value = "AGR")
    private ArrayList<QuestionDto> agr;
    @JsonProperty(value = "CSN")
    private ArrayList<QuestionDto> csn;
    @JsonProperty(value = "OPN")
    private ArrayList<QuestionDto> opn;

    public SurveyDto() {
    }

    public SurveyDto(ArrayList<QuestionDto> ext, ArrayList<QuestionDto> est, ArrayList<QuestionDto> agr, ArrayList<QuestionDto> csn, ArrayList<QuestionDto> opn) {
        this.ext = ext;
        this.est = est;
        this.agr = agr;
        this.csn = csn;
        this.opn = opn;
    }

    public ArrayList<QuestionDto> getExt() {
        return ext;
    }

    public void setExt(ArrayList<QuestionDto> ext) {
        this.ext = ext;
    }

    public ArrayList<QuestionDto> getEst() {
        return est;
    }

    public void setEst(ArrayList<QuestionDto> est) {
        this.est = est;
    }

    public ArrayList<QuestionDto> getAgr() {
        return agr;
    }

    public void setAgr(ArrayList<QuestionDto> agr) {
        this.agr = agr;
    }

    public ArrayList<QuestionDto> getCsn() {
        return csn;
    }

    public void setCsn(ArrayList<QuestionDto> csn) {
        this.csn = csn;
    }

    public ArrayList<QuestionDto> getOpn() {
        return opn;
    }

    public void setOpn(ArrayList<QuestionDto> opn) {
        this.opn = opn;
    }
}

