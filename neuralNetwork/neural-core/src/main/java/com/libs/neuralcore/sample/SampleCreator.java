package com.libs.neuralcore.sample;

import com.libs.neuralcore.exceptions.ParameterException;

public interface SampleCreator {

    void downloadSample() throws ParameterException;

    void unpackSample() throws ParameterException;

    void clear() throws ParameterException;
}