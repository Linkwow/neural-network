package com.libs.neuralcore.config;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.praparer.DataPreparer;
import com.libs.neuralcore.exceptions.InitializeException;
import com.libs.neuralcore.sample.impl.SampleCreatorImpl;
import com.libs.neuralcore.sample.SampleCreator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    @Value("${download.url:}")
    private String downloadUrl;

    @Value("${download.path:}")
    private String downloadPath;

    @Value("${sample.file:}")
    private String sampleFile;

    @Value("${unpack.path:}")
    private String unpackPath;

    @Value("${innerUnpack.path:}")
    private String innerUnpackPath;

    @Value("${inner.file:}")
    private String innerFile;

    @Value("${sample.path}")
    private String samplePath;

    @Value("${height}")
    private int height;

    @Value("${width}")
    private int width;

    @Value("${channels}")
    private int channels;

    @Value("${batchSize}")
    private int batchSize;

    @Value("${outputNum}")
    private int outputNum;

    @Value("${numEpochs}")
    private int numEpochs;

    @Bean
    public SampleCreator initSampleCreatorImpl () throws InitializeException {
        return new SampleCreatorImpl(downloadUrl, downloadPath, sampleFile, unpackPath, innerUnpackPath, innerFile);
    }

    @Bean
    public DataPreparer initDataPreparer(){
        DataPreparer dataPreparer = new DataPreparer(height, width, channels, batchSize, outputNum);
        dataPreparer.setSamplePath(samplePath);
        return dataPreparer;
    }

    @Bean
    public ModelBuilder initModelBuilder(){
        return new ModelBuilder(height, width, channels, numEpochs, outputNum);
    }
}
