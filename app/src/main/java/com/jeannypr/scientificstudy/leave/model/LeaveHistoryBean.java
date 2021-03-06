package com.jeannypr.scientificstudy.leave.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jeannypr.scientificstudy.Base.Model.Bean;

import java.util.List;

public class LeaveHistoryBean extends Bean {
    @SerializedName("data")
    @Expose
    public List<LeaveHistoryModel> data;
}
