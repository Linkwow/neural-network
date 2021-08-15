package com.libs.naumenko.neuralcore.data.unpack;

import net.lingala.zip4j.ZipFile;
import net.lingala.zip4j.exception.ZipException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.io.File;
import java.io.IOException;

public class SampleUnpack {

    private final Logger logger = LoggerFactory.getLogger(SampleUnpack.class);

    private ZipFile fileFromSource;

    private final String downloadPath;

    private final String unpackPath;

    private final String innerZipUnpackPath;

    private final String innerZipFile;

    @Autowired
    public void setFileFromSource(ZipFile fileFromSource) {
        this.fileFromSource = fileFromSource;
    }

    public SampleUnpack(String downloadPath, String unpackPath, String innerZipUnpackPath, String innerZipFile) {
        this.unpackPath = unpackPath;
        this.innerZipUnpackPath = innerZipUnpackPath;
        this.innerZipFile = innerZipFile;
        this.downloadPath = downloadPath;
    }

    public File[] unpackSample() throws IOException {
        try {
            fileFromSource.extractAll(unpackPath);
            //this part was added in case when you have your sample pack with inner pack (e.g. from gitHub)
            if (!innerZipUnpackPath.isBlank()) {
                ZipFile innerZip = new ZipFile(unpackPath + "\\" + innerZipFile);
                innerZip.extractAll(innerZipUnpackPath);
                return new File(innerZipUnpackPath).listFiles();
            }
            return new File(unpackPath).listFiles();
        } catch (ZipException e) {
            logger.error("Error while unpacking sample");
            throw new RuntimeException(e.getMessage());
        }
    }

    public void clear() throws IOException {
        FileUtils.deleteDirectory(new File(unpackPath));
        FileUtils.deleteDirectory(new File(downloadPath));
    }
}