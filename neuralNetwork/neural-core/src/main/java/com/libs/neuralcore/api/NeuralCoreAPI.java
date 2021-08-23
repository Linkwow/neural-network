package com.libs.neuralcore.api;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.preparer.DataPreparer;
import com.libs.neuralcore.sample.SampleCreator;
import net.lingala.zip4j.exception.ZipException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.List;

@Component
public class NeuralCoreAPI {

    private SampleCreator sampleCreator;

    private DataPreparer<DataSetIterator> dataPreparer;

    private ModelBuilder<DataSetIterator> modelBuilder;

    private List<Object> dataSets;

    private List<Object> labels;

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

    public NeuralCoreAPI() {
        parseDataSetAndLabels();
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

    public MultiLayerNetwork trainModel() {
        modelBuilder.configureModel();
        modelBuilder.initializeModel();
        return modelBuilder.trainModel(dataPreparer.createTrainDataSetIterator());
    }

    public Evaluation evaluateModel() {
       return modelBuilder.evaluateModel(dataPreparer.createTestDataSetIterator());
    }

    private void parseDataSetAndLabels(){
        DataSetIterator demoIterator = dataPreparer.createDemoDataSetIterator();
        List<Object> objectList = dataPreparer.demo(demoIterator);
        for (int i = 0; i < objectList.size(); i++) {
            if(i % 2 == 0)
                dataSets.add(objectList.get(i));
            else
                labels.add(objectList.get(i));
        }
    }

    public List<Object> getDataSets() {
        return dataSets;
    }

    public List<Object> getLabels() {
        return labels;
    }
}