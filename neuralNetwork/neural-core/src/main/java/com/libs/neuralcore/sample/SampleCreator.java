package com.libs.neuralcore.sample;

import net.lingala.zip4j.exception.ZipException;
import java.io.IOException;

public interface SampleCreator {

    void downloadSample() throws IOException;

    void unpackSample() throws ZipException;

    void clear() throws IOException;
}