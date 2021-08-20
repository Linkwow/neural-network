package com.libs.neuralcore.api;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.praparer.DataPreparer;
import com.libs.neuralcore.sample.impl.SampleCreatorImpl;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class NeuralCoreAPI {

    private SampleCreatorImpl sample;

    private DataPreparer<DataSetIterator> dataPreparer;

    private ModelBuilder<DataSetIterator> modelBuilder;

    @Autowired
    public void setSample(SampleCreatorImpl sample) {
        this.sample = sample;
    }

    @Autowired
    public void setDataPreparer(DataPreparer<DataSetIterator> dataPreparer) {
        this.dataPreparer = dataPreparer;
    }

    @Autowired
    public void setModelBuilder(ModelBuilder<DataSetIterator> modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    public void downloadSample(){
        sample.downloadSample();
    }

    public void unpackSample(){
        sample.unpackSample();
    }

    public void removeSampleArchive(){
        sample.clear();
    }

    public void trainModel(){
        modelBuilder.configureModel();
        modelBuilder.initializeModel();
        modelBuilder.trainModel(dataPreparer.createTrainDataSet());
    }

    public void evaluateModel(){
        modelBuilder.evaluateModel(dataPreparer.createTestDataSet());
    }
}
