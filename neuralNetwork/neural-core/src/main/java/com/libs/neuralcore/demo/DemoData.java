package com.libs.neuralcore.demo;

import java.util.ArrayList;
import java.util.List;

public class DemoData {

    private final List<DemoDataSet> dataSetList = new ArrayList<>();

    private String labels;

    public void setData(List<String> dataSet) {
        DemoDataSet demoDataSet;
        for (String s : dataSet) {
            demoDataSet = new DemoDataSet();
            demoDataSet.setDataSet(s);
            dataSetList.add(demoDataSet);
        }
    }

    public void setLabels(String labels) {
        this.labels = labels;
    }

    @SuppressWarnings("unused")
    public List<DemoDataSet> getData() {
        return dataSetList;
    }

    @SuppressWarnings("unused")
    public String getLabels() {
        return labels;
    }
}
