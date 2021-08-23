package com.libs.neuralcore.data.preparer;

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import java.util.List;

public interface DataPreparer<T> {

    T createTrainDataSetIterator();

    T createTestDataSetIterator();

    T createDemoDataSetIterator();

    List<Object> demo(DataSetIterator dataSetIterator);
}