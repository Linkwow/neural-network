package com.neural.application.service;


import com.libs.neuralcore.api.NeuralCoreAPI;
import com.libs.neuralcore.exceptions.ParameterException;
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

    public String downloadSampleFromSource() throws ParameterException {
            neuralCoreAPI.downloadSample();
            neuralCoreAPI.unpackSample();
            neuralCoreAPI.removeSampleArchive();
            return "Downloading and unpacking was finished.";
    }

    public List<Object> getLabels(){
       return neuralCoreAPI.getLabels();
    }

    public List<Object> getDataSets(){
        return neuralCoreAPI.getDataSets();
    }

    public void createDataForDemo(){
        neuralCoreAPI.createDataForDemo();
    }

}
