package com.libs.neuralcore.data.builder;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;

public interface ModelBuilder<T> {

    void configureModel();

    void initializeModel();

    MultiLayerNetwork trainModel(T trainDataIterator);

    Evaluation evaluateModel(T testDataIterator);
}