package com.neural.application.service;


import com.libs.neuralcore.api.NeuralCoreAPI;
import com.libs.neuralcore.demo.DemoData;
import com.libs.neuralcore.demo.DemoDataSet;
import com.libs.neuralcore.exceptions.ParameterException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.List;

@Service
public class NeuralService {

    private NeuralCoreAPI neuralCoreAPI;

    private MultiLayerNetwork model;

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

    public List<DemoData> createDataForDemo() {
        return neuralCoreAPI.createDataForDemo();
    }

    public File trainModel() throws ParameterException {
        return neuralCoreAPI.saveModel(neuralCoreAPI.trainModel());
    }

    public List<DemoDataSet> evaluateModel() throws ParameterException {
        model = neuralCoreAPI.loadModel();
        return neuralCoreAPI.evaluateModel(model);
    }

    public String checkModel() throws ParameterException {
        model = neuralCoreAPI.loadModel();
        return neuralCoreAPI.checkTheImage(model);
    }

    public String getCheckLabels() {
        return neuralCoreAPI.getCheckLabels();
    }
}
