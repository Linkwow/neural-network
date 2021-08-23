package com.neural.application.service;


import com.libs.neuralcore.api.NeuralCoreAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class NeuralService {

    private NeuralCoreAPI neuralCoreAPI;

    @Autowired
    public void setNeuralCoreAPI(NeuralCoreAPI neuralCoreAPI) {
        this.neuralCoreAPI = neuralCoreAPI;
    }

    public String createNeuralNetworkModel() {
        try {
            neuralCoreAPI.downloadSample();
            neuralCoreAPI.unpackSample();
            neuralCoreAPI.removeSampleArchive();
            neuralCoreAPI.trainModel();
            neuralCoreAPI.evaluateModel();
            return "Download Complete";
        } catch (Exception e) {
            throw new RuntimeException(e.getMessage());
        }
    }

    public List<Object> getLabels(){
       return neuralCoreAPI.getLabels();
    }

    public List<Object> getDataSets(){
        return neuralCoreAPI.getDataSets();
    }

}
