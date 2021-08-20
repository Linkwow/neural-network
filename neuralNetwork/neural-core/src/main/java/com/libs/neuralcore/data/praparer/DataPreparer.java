package com.libs.neuralcore.data.praparer;

public interface DataPreparer<T> {

    T createTrainDataSet();

    T createTestDataSet();

}
