package com.libs.neuralcore.config;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.builder.impl.ModelBuilderImpl;
import com.libs.neuralcore.data.preparer.DataPreparer;
import com.libs.neuralcore.data.preparer.impl.DataPreparerImpl;
import com.libs.neuralcore.exceptions.InitializeException;
import com.libs.neuralcore.sample.impl.SampleCreatorImpl;
import com.libs.neuralcore.sample.SampleCreator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
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

    @Value("${seed}")
    private int seed;

    @Bean
    public SampleCreator initSampleCreatorImpl () throws InitializeException {
        return new SampleCreatorImpl(downloadUrl, downloadPath, sampleFile, unpackPath, innerUnpackPath, innerFile);
    }

    @Bean
    public DataPreparer<DataSetIterator> initDataPreparer(){
        return new DataPreparerImpl(samplePath, height, width, channels, batchSize, outputNum, seed);
    }

    @Bean
    public ModelBuilder<DataSetIterator> initModelBuilderImpl(){
        return new ModelBuilderImpl(height, width, channels, numEpochs, outputNum, seed);
    }
}
