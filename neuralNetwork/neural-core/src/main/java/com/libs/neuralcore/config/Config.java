package com.libs.neuralcore.config;

import com.libs.neuralcore.data.builder.impl.ModelBuilderImpl;
import com.libs.neuralcore.data.praparer.impl.DataPreparerImpl;
import com.libs.neuralcore.sample.impl.SampleCreatorImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final Logger logger = LoggerFactory.getLogger(Config.class);

    @Value("${download.url:")
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
    public SampleCreatorImpl initSampleCreatorImpl () {
        SampleCreatorImpl sampleCreatorImpl = new SampleCreatorImpl();
        if (!downloadUrl.isEmpty()) {
            logger.debug("Reading download url");
            sampleCreatorImpl.setUrl(downloadUrl);
        }
        if (!sampleFile.isEmpty()) {
            logger.debug("Reading download path");
            sampleCreatorImpl.setDownloadPath(downloadPath);
            logger.debug("Reading sample file name");
            sampleCreatorImpl.setSampleFile(sampleFile);
        }
        if (innerUnpackPath.isEmpty()) {
            logger.debug("Setting inner pack paths");
            sampleCreatorImpl.setInnerUnpackPath(innerUnpackPath);
            sampleCreatorImpl.setUnpackPath(unpackPath);
            sampleCreatorImpl.setInnerFile(innerFile);
        }
        logger.debug("SampleCreatorImpl was created successfully");
        return new SampleCreatorImpl();
    }

    @Bean
    public DataPreparerImpl initDataPreparer(){
        DataPreparerImpl dataPreparerImpl = new DataPreparerImpl(height, width, channels, batchSize, outputNum);
        dataPreparerImpl.setSamplePath(samplePath);
        return dataPreparerImpl;
    }

    @Bean
    public ModelBuilderImpl initModelBuilder(){
        return new ModelBuilderImpl(height, width, channels, numEpochs, outputNum);
    }
}
