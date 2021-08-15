package com.libs.naumenko.neuralcore;

import com.libs.naumenko.neuralcore.data.load.SampleLoader;
import com.libs.naumenko.neuralcore.data.unpack.SampleUnpack;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;

@Service
public class NeuralCoreService {

    private SampleLoader sampleLoader;

    private SampleUnpack sampleUnpack;

    @Autowired
    public void setSampleLoader(SampleLoader sampleLoader) {
        this.sampleLoader = sampleLoader;
    }

    @Autowired
    public void setSampleUnpack(SampleUnpack sampleUnpack) {
        this.sampleUnpack = sampleUnpack;
    }

    public void loadSampleFromSource() throws IOException {
        sampleLoader.downloadSample();
        File[] files = sampleUnpack.unpackSample();

    }
}
