package com.libs.neuralcore.api;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.preparer.DataPreparer;
import com.libs.neuralcore.exceptions.ParameterException;
import com.libs.neuralcore.sample.SampleCreator;
import net.lingala.zip4j.exception.ZipException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.nd4j.evaluation.classification.Evaluation;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class NeuralCoreAPI {

    private SampleCreator sampleCreator;

    private DataPreparer<DataSetIterator> dataPreparer;

    private ModelBuilder<DataSetIterator> modelBuilder;

    private final List<Object> dataSet = new ArrayList<>();

    private final List<Object> labels = new ArrayList<>();

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

    public Evaluation evaluateModel() {
        return modelBuilder.evaluateModel(dataPreparer.createTestDataSetIterator());
    }

    public void createDataForDemo() {
        DataSetIterator demoIterator = dataPreparer.createDemoDataSetIterator();
        List<Object> objectList = dataPreparer.demo(demoIterator);
        for (int i = 0; i < objectList.size(); i++) {
            if (i % 2 == 0)
                dataSet.add(objectList.get(i));
            else
                labels.add(objectList.get(i));
        }
    }

    //fixme : here should be List<DataSet>
    public List<Object> getDataSets() {
        return dataSet;
    }

    //fixme : here should be List<String>
    public List<Object> getLabels() {
        return labels;
    }
}