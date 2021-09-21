package com.neural.application.service;


import com.libs.neuralcore.api.NeuralCoreAPI;
import com.libs.neuralcore.entity.DemoData;
import com.libs.neuralcore.entity.DemoDataSet;
import com.libs.neuralcore.exceptions.ParameterException;
import com.neural.application.entity.CheckModelEntity;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

@Service
public class NeuralService {

    private NeuralCoreAPI neuralCoreAPI;

    private MultiLayerNetwork model;

    @Value("${outputNum}")
    private Integer outputNum;

    @Autowired
    public void setNeuralCoreAPI(NeuralCoreAPI neuralCoreAPI) {
        this.neuralCoreAPI = neuralCoreAPI;
    }

    public String downloadSampleFromSource() throws ParameterException {
        neuralCoreAPI.downloadSample();
        neuralCoreAPI.unpackSample();
        neuralCoreAPI.removeSampleArchive();
        return "Downloading and unpacking was finished.";
    }

    public List<DemoData> demonstrateModel() {
        return neuralCoreAPI.createDataForDemo();
    }

    public File trainModel() throws ParameterException {
        return neuralCoreAPI.saveModel(neuralCoreAPI.trainModel());
    }

    public List<DemoDataSet> evaluateModel() throws ParameterException {
        model = neuralCoreAPI.loadModel();
        return neuralCoreAPI.evaluateModel(model);
    }

    public List<CheckModelEntity> checkModel() throws ParameterException {
        List<CheckModelEntity> checkModelEntities = new ArrayList<>();
        String[] labels = getLabelsList().split(", ");
        String[] results = getResults().split(", ");
        for (int i = 0; i < outputNum; i++) {
            CheckModelEntity checkModelEntity = new CheckModelEntity();
            checkModelEntity.setLabel(labels[i]);
            checkModelEntity.setResult(results[i]);
            checkModelEntities.add(checkModelEntity);
        }
        return checkModelEntities;
    }

    private String getResults() throws ParameterException {
        model = neuralCoreAPI.loadModel();
        return neuralCoreAPI.checkImage(model);
    }

    private String getLabelsList() {
        return neuralCoreAPI.getLabelsList();
    }
}
