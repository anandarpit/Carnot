package com.example.carnot.retrofit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GovDataModel {

    @SerializedName("records")
    @Expose
    private List<Record> records = null;

    public List<Record> getRecord() {
        return records;
    }

    public void setRecord(List<Record> records) {
        this.records = records;
    }
}
