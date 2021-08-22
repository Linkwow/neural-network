package com.libs.neuralcore.api;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.praparer.DataPreparer;
import com.libs.neuralcore.sample.SampleCreator;
import net.lingala.zip4j.exception.ZipException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class NeuralCoreAPI {

    private SampleCreator sampleCreator;

    private DataPreparer dataPreparer;

    private ModelBuilder modelBuilder;

    @Autowired
    protected void setSampleCreator(SampleCreator sampleCreator) {
        this.sampleCreator = sampleCreator;
    }

    @Autowired
    protected void setDataPreparer(DataPreparer dataPreparer) {
        this.dataPreparer = dataPreparer;
    }

    @Autowired
    protected void setModelBuilder(ModelBuilder modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    public void downloadSample() throws IOException {
        sampleCreator.downloadSample();
    }

    public void unpackSample() throws ZipException {
        sampleCreator.unpackSample();
    }

    public void removeSampleArchive() throws IOException {
        sampleCreator.clear();
    }

    public void trainModel() {
        modelBuilder.configureModel();
        modelBuilder.initializeModel();
        modelBuilder.trainModel(dataPreparer.createTrainDataSet());
    }

    public void evaluateModel() {
        modelBuilder.evaluateModel(dataPreparer.createTestDataSet());
    }
}
