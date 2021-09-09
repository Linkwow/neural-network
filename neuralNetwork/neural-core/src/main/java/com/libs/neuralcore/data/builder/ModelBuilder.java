package com.libs.neuralcore.data.builder;

import com.libs.neuralcore.demo.DemoDataSet;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.util.List;

public interface ModelBuilder<T> {

    void configureModel();

    void initializeModel();

    MultiLayerNetwork trainModel(T trainDataIterator);

    List<DemoDataSet> evaluateModel(T testDataIterator, MultiLayerNetwork model);
}