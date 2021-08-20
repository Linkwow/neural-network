package com.libs.neuralcore.sample.impl;

import com.libs.neuralcore.sample.SampleCreator;
import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.Path;

public class SampleCreatorImpl {

    private final Logger logger = LoggerFactory.getLogger(SampleCreatorImpl.class);

    private URL url;

    private String downloadPath;

    private File sampleFile;

    private String unpackPath;

    private String innerUnpackPath;

    private String innerFile;

    private ZipFile zipFile;

    public void setUrl(String downloadUrl) {
        if (!downloadUrl.isEmpty()) {
            try {
                this.url = new URL(downloadUrl);
            } catch (MalformedURLException e) {
                logger.error("Error during initialize url to download");
                throw new RuntimeException(e);
            }
        }
    }

    public void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    public void setSampleFile(String sampleFile) {
        this.sampleFile = Path.of(downloadPath + "\\" + sampleFile).toFile();
    }

    public void setUnpackPath(String unpackPath) {
        this.unpackPath = unpackPath;
    }

    public void setInnerUnpackPath(String innerUnpackPath) {
        this.innerUnpackPath = innerUnpackPath;
    }

    public void setInnerFile(String innerFile) {
        this.innerFile = innerFile;
    }

    //@Override
    public void downloadSample() {
        try {
            FileUtils.copyURLToFile(url, sampleFile);
        } catch (IOException e) {
            logger.error("Error during download sample");
            throw new RuntimeException(e);
        }
    }

   // @Override
    public void unpackSample() {
        try {
            zipFile.extractAll(unpackPath);
            //this condition was added in case when your sample pack has inner pack (e.g. from gitHub)
            if (hasInnerPack())
                innerPack();
        } catch (ZipException e) {
            logger.error("Error while unpacking sample");
            throw new RuntimeException(e.getMessage());
        }
    }

    //@Override
    public void clear() {
        try {
            FileUtils.deleteDirectory(Path.of(downloadPath).toFile());
            if (hasInnerPack())
                FileUtils.deleteDirectory(Path.of(unpackPath).toFile());
        } catch (IOException e) {
            logger.error("No directory to clean");
            throw new RuntimeException(e.getMessage());
        }
    }

    private void innerPack() {
        try {
            zipFile = new ZipFile(innerFile);
            zipFile.extractAll(innerUnpackPath);
        } catch (ZipException e) {
            logger.error("Error while unpacking sample");
            throw new RuntimeException(e.getMessage());
        }
    }

    private boolean hasInnerPack() {
        return innerUnpackPath.isEmpty();
    }
}
