package com.libs.neuralcore.data.builder;

public interface ModelBuilder<T> {

    void configureModel();

    void initializeModel();

    void trainModel(T trainData);

    void evaluateModel(T testData);

}
