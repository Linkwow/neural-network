package com.neural.application.service;


import com.libs.neuralcore.api.NeuralCoreAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NeuralService {

    private NeuralCoreAPI neuralCoreAPI;

    @Autowired
    public void setNeuralCoreAPI(NeuralCoreAPI neuralCoreAPI) {
        this.neuralCoreAPI = neuralCoreAPI;
    }

    public String createNeuralNetworkModel(){
        neuralCoreAPI.downloadSample();
        neuralCoreAPI.unpackSample();
        neuralCoreAPI.removeSampleArchive();
        return "Download Complete";
    }
}
