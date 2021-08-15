package com.libs.naumenko.neuralcore.data.load;

import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.net.URL;

@Component
public class SampleLoader {

    private final Logger logger = LoggerFactory.getLogger(SampleLoader.class);

    private URL url;

    private File file;

    @Autowired
    public void setUrl(URL url) {
        this.url = url;
    }

    @Autowired
    public void setFile(File file) {
        this.file = file;
    }

    public void downloadSample() throws IOException {
        try {
            FileUtils.copyURLToFile(url, file);
        } catch (IOException e) {
            logger.error("Error during download sample");
            throw new RuntimeException(e);
        }
    }
}
