package com.libs.neuralcore.data.preparer;

import org.nd4j.linalg.dataset.DataSet;
import java.util.List;

public interface DataPreparer<T> {

    T createTrainDataSetIterator();

    T createTestDataSetIterator();

    void createDemoDataSet();

    List<DataSet> getDataSets();

    List<List<String>> getLabels();
}