package com.libs.neuralcore.data.interact;

import com.libs.neuralcore.exceptions.ParameterException;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;

import java.io.File;

public interface ModelInteract {

    File save(MultiLayerNetwork model) throws ParameterException;

    MultiLayerNetwork load(File file) throws ParameterException;

    String checkImage(MultiLayerNetwork model) throws ParameterException;

    String getCheckLabelList();
}
