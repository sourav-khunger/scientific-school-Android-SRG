package com.jeannypr.scientificstudy.Exam.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jeannypr.scientificstudy.Base.Model.Bean;

import java.util.List;

public class ExamRoasterBean extends Bean {
    @SerializedName("data")
    @Expose
    public List<ExamRoasterModel> data;
}
