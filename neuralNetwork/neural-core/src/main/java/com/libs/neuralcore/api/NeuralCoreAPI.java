package com.libs.neuralcore.api;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.interact.ModelInteract;
import com.libs.neuralcore.data.preparer.DataPreparer;
import com.libs.neuralcore.demo.DemoData;
import com.libs.neuralcore.demo.DemoDataSet;
import com.libs.neuralcore.exceptions.ParameterException;
import com.libs.neuralcore.sample.SampleCreator;

import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.util.List;

@Component
public class NeuralCoreAPI {

    private SampleCreator sampleCreator;

    private DataPreparer<DataSetIterator> dataPreparer;

    private ModelBuilder<DataSetIterator> modelBuilder;

    private ModelInteract modelInteract;

    @Autowired
    protected void setSampleCreator(SampleCreator sampleCreator) {
        this.sampleCreator = sampleCreator;
    }

    @Autowired
    protected void setDataPreparer(DataPreparer<DataSetIterator> dataPreparer) {
        this.dataPreparer = dataPreparer;
    }

    @Autowired
    protected void setModelBuilder(ModelBuilder<DataSetIterator> modelBuilder) {
        this.modelBuilder = modelBuilder;
    }

    @Autowired
    public void setModelInteract(ModelInteract modelInteract) {
        this.modelInteract = modelInteract;
    }

    public void downloadSample() throws ParameterException {
        sampleCreator.downloadSample();
    }

    public void unpackSample() throws ParameterException {
        sampleCreator.unpackSample();
    }

    public void removeSampleArchive() throws ParameterException {
        sampleCreator.clear();
    }

    public MultiLayerNetwork trainModel() {
        modelBuilder.configureModel();
        modelBuilder.initializeModel();
        return modelBuilder.trainModel(dataPreparer.createTrainDataSetIterator());
    }

    public File saveModel(MultiLayerNetwork model) throws ParameterException {
        return modelInteract.save(model);
    }

    public MultiLayerNetwork loadModel() throws ParameterException {
        return modelInteract.load();
    }

    public List<DemoDataSet> evaluateModel(MultiLayerNetwork model) {
        return modelBuilder.evaluateModel(dataPreparer.createTestDataSetIterator(), model);
    }

    public List<DemoData> createDataForDemo() {
        return dataPreparer.createDemoDataSet();
    }

    public String checkTheImage(MultiLayerNetwork model) throws ParameterException {
        return modelInteract.checkImage(model);
    }

    public String getCheckLabels() {
        return modelInteract.getCheckLabelList();
    }
}