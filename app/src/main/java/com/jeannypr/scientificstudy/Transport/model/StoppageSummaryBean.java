package com.jeannypr.scientificstudy.Transport.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.jeannypr.scientificstudy.Base.Model.Bean;

import java.util.List;

public class StoppageSummaryBean extends Bean {

    @SerializedName("data")
    @Expose
    public List<StoppageSummaryModel> data;
}