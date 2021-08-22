package com.libs.neuralcore.config;

import com.libs.neuralcore.data.builder.ModelBuilder;
import com.libs.neuralcore.data.praparer.DataPreparer;
import com.libs.neuralcore.sample.SampleCreator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class Config {

    private final Logger logger = LoggerFactory.getLogger(Config.class);

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
    public SampleCreator initSampleCreatorImpl () {
        SampleCreator sampleCreator = new SampleCreator();
        if (!downloadUrl.isBlank()) {
            logger.debug("Reading download url");
            sampleCreator.setUrl(downloadUrl);
        }
        if (!sampleFile.isBlank()) {
            logger.debug("Reading download path");
            sampleCreator.setDownloadPath(downloadPath);
            logger.debug("Reading sample file name");
            sampleCreator.setSampleFile(sampleFile);
        }
        if (!innerUnpackPath.isBlank()) {
            logger.debug("Setting inner pack paths");
            sampleCreator.setInnerUnpackPath(innerUnpackPath);
            sampleCreator.setUnpackPath(unpackPath);
            sampleCreator.setInnerFile(innerFile);
        }
        logger.debug("SampleCreator was created successfully");
        return sampleCreator;
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
