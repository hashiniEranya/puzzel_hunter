package com.example.nb_minegame.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class QuizResponse {
    @SerializedName("question")
    @Expose
    private String question;
    @SerializedName("solution")
    @Expose
    private Integer solution;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public Integer getSolution() {
        return solution;
    }

    public void setSolution(Integer solution) {
        this.solution = solution;
    }
}
