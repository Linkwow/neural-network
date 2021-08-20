package com.libs.neuralcore.data.builder.impl;

import com.libs.neuralcore.data.builder.ModelBuilder;

import org.deeplearning4j.nn.api.OptimizationAlgorithm;
import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
import org.deeplearning4j.nn.conf.inputs.InputType;
import org.deeplearning4j.nn.conf.layers.DenseLayer;
import org.deeplearning4j.nn.conf.layers.OutputLayer;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.nn.weights.WeightInit;
import org.deeplearning4j.optimize.listeners.ScoreIterationListener;

import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.activations.impl.ActivationReLU;
import org.nd4j.linalg.activations.impl.ActivationSoftmax;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.learning.config.Adam;
import org.nd4j.linalg.learning.config.Nesterovs;
import org.nd4j.linalg.lossfunctions.LossFunctions;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ModelBuilderImpl implements ModelBuilder<DataSetIterator> {

    private final Logger logger = LoggerFactory.getLogger(ModelBuilderImpl.class);

    private MultiLayerConfiguration configuration;

    private MultiLayerNetwork model;

    private final int height;

    private final int width;

    private final int channels;

    private final int numEpochs;

    private final int outputNum;

    public ModelBuilderImpl(int height, int width, int channels, int numEpochs, int outputNum) {
        this.height = height;
        this.width = width;
        this.channels = channels;
        this.numEpochs = numEpochs;
        this.outputNum = outputNum;
    }

    @Override
    public void configureModel() {
        configuration = new NeuralNetConfiguration.Builder()
                //   .seed(rngSeed)
                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
                .updater(new Adam(0.006))
                .updater(new Nesterovs(0.9))
                .l2(1e-4)
                .list()
                .layer(0, new DenseLayer.Builder()
                        .nIn(height * width)
                        .nOut(100)
                        .activation(new ActivationReLU())
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
                        .nIn(100)
                        .nOut(10)
                        .activation(new ActivationSoftmax())
                        .weightInit(WeightInit.XAVIER)
                        .build())
                .setInputType(InputType.convolutional(height, width, channels))
                .build();
    }

    @Override
    public void initializeModel() {
        model = new MultiLayerNetwork(configuration);
        model.init();
    }

    @Override
    public void trainModel(DataSetIterator dataSetIterator) {
        model.pretrain(dataSetIterator, numEpochs);
        model.fit(dataSetIterator, numEpochs);
        model.setListeners(new ScoreIterationListener(10));
    }

    @Override
    public void evaluateModel(DataSetIterator testDataSet) {
        Evaluation evaluation = new Evaluation(outputNum);
        while (testDataSet.hasNext()) {
            DataSet next = testDataSet.next();
            INDArray output = model.output(next.getFeatures());
            evaluation.eval(next.getLabels(), output);
        }
        //is it necessary?
        logger.info(evaluation.stats());
    }

}
