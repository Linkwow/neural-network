package com.libs.neuralcore.sample.impl;

import com.libs.neuralcore.exceptions.InitializeException;
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

public class SampleCreatorImpl implements SampleCreator {

    @SuppressWarnings("FieldCanBeLocal")
    private final Logger logger = LoggerFactory.getLogger(SampleCreatorImpl.class);

    private URL url;

    private String downloadPath;

    private File sampleFile;

    private String unpackPath;

    private String innerUnpackPath;

    private String innerFile;

    private ZipFile zipFile;

    public SampleCreatorImpl(
            String downloadUrl,
            String downloadPath,
            String sampleFile,
            String unpackPath,
            String innerUnpackPath,
            String innerFile) throws InitializeException {
        try {
            logger.debug("Sample Creator is being initializing now");
            this.setUrl(downloadUrl);
            this.setDownloadPath(downloadPath);
            this.setSampleFile(sampleFile);
            this.setUnpackPath(unpackPath);
            this.setInnerUnpackPath(innerUnpackPath);
            this.setInnerFile(innerFile);
            logger.debug("Sample Creator was initialized successfully");
        } catch (MalformedURLException e) {
            logger.error("Exception during Sample Creator is being initializing. Check url path");
            throw new InitializeException(e.getMessage());
        }
    }

    public void downloadSample() throws IOException {
        FileUtils.copyURLToFile(url, sampleFile);
    }

    public void unpackSample() throws ZipException {
        zipFile = new ZipFile(sampleFile);
        zipFile.extractAll(unpackPath);
        //this condition was added in case when your sample pack has inner pack (e.g. from gitHub)
        if (hasInnerPack())
            innerPack();
    }

    public void clear() throws IOException {
        FileUtils.deleteDirectory(Path.of(downloadPath).toFile());
        if (hasInnerPack())
            FileUtils.deleteDirectory(Path.of(unpackPath).toFile());
    }

    private void setUrl(String downloadUrl) throws MalformedURLException {
        if (!downloadUrl.isBlank())
            this.url = new URL(downloadUrl);
    }

    private void setDownloadPath(String downloadPath) {
        this.downloadPath = downloadPath;
    }

    private void setSampleFile(String sampleFile) {
        if (!sampleFile.isBlank())
            this.sampleFile = Path.of(downloadPath + "\\" + sampleFile).toFile();
    }

    private void setUnpackPath(String unpackPath) {
        this.unpackPath = unpackPath;
    }

    private void setInnerUnpackPath(String innerUnpackPath) {
        this.innerUnpackPath = innerUnpackPath;
    }

    private void setInnerFile(String innerFile) {
        this.innerFile = innerFile;
    }

    private void innerPack() throws ZipException {
        zipFile = new ZipFile(unpackPath + "\\" + innerFile);
        zipFile.extractAll(innerUnpackPath);
    }

    private boolean hasInnerPack() {
        return !innerUnpackPath.isBlank();
    }
}
