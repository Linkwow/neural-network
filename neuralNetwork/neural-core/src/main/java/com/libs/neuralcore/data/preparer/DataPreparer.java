package com.libs.neuralcore.data.preparer;

import com.libs.neuralcore.entity.DemoData;
import java.util.List;

public interface DataPreparer<T> {

    T createTrainDataSetIterator();

    T createTestDataSetIterator();

    List<DemoData> createDemoDataSet();
}