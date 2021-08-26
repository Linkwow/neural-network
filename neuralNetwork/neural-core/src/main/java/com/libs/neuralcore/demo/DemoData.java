package com.libs.neuralcore.demo;

public class DemoData {

    private String dataSet;

    private String labels;

    public void setDataSet(String dataSet) {
        this.dataSet = dataSet;
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    @SuppressWarnings("unused")
    public String getDataSet() {
        return dataSet;
    }

    @SuppressWarnings("unused")
    public String getLabels() {
        return labels;
    }
}
