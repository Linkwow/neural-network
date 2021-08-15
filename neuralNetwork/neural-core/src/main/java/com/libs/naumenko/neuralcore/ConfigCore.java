package com.libs.naumenko.neuralcore;

import com.libs.naumenko.neuralcore.data.load.SampleLoader;
import com.libs.naumenko.neuralcore.data.unpack.SampleUnpack;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;

import net.lingala.zip4j.ZipFile;

@Configuration
public class ConfigCore {

    private final Logger logger = LoggerFactory.getLogger(ConfigCore.class);

    @Value("${download.url}")
    private String downloadUrl;

    @Value("${download.path:}")
    private String downloadPath;

    @Value("${download.file}")
    private String downloadFile;

    @Value("${unpack.path:}")
    private String unpackPath;

    @Value("${innerZipUnpack.path:}")
    private String innerZipUnpackPath;

    @Value("${innerZip.file}")
    private String innerZipFile;

    @Bean
    public URL initURL(){
        try {
            return new URL(downloadUrl);
        } catch (MalformedURLException e) {
            logger.error("Error during initialize url to download" + e.getMessage());
            throw new BeanCreationException(e.getMessage());
        }
    }

    @Bean
    public File initFilePath(){
        return new File(downloadPath + "\\" + downloadFile);
    }

    @Bean
    public SampleLoader initSampleLoader(){
        return new SampleLoader();
    }

    @Bean
    public ZipFile initZipFile(){
        return new ZipFile(downloadPath + "\\" + downloadFile);
    }

    @Bean
    public SampleUnpack initSampleUnpack(){
        return new SampleUnpack(downloadPath, unpackPath, innerZipUnpackPath, innerZipFile);
    }
}
