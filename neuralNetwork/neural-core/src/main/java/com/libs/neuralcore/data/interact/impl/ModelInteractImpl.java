package com.libs.neuralcore.data.interact.impl;

import com.libs.neuralcore.data.interact.ModelInteract;
import com.libs.neuralcore.exceptions.ParameterException;
import com.libs.neuralcore.util.Constants;
import org.datavec.image.loader.NativeImageLoader;
import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
import org.deeplearning4j.util.ModelSerializer;
import org.nd4j.linalg.api.ndarray.INDArray;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.*;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class ModelInteractImpl implements ModelInteract {

    @SuppressWarnings("FieldMayBeFinal")
    private static Logger logger  = LoggerFactory.getLogger(ModelInteractImpl.class);

    private final File file;

    private final int height;

    private final int width;

    private final int channels;

    private final List<Integer> labelList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);

    public ModelInteractImpl(String fileName, int height, int width, int channels){
        this.file = new File(fileName);
        this.height = height;
        this.width = width;
        this.channels = channels;
    }

    @Override
    public File save(MultiLayerNetwork model) throws ParameterException {
        try {
            ModelSerializer.writeModel(model, file, false);
        } catch (IOException e) {
            logger.error("Error during model was being saved into " + file.getAbsolutePath());
            throw new ParameterException(e.getMessage());
        }
        return file;
    }

    @Override
    public MultiLayerNetwork load() throws ParameterException {
        try {
            return ModelSerializer.restoreMultiLayerNetwork(file);
        } catch (IOException e) {
            logger.error("Error during model was being loaded from " + file);
            throw new ParameterException(e.getMessage());
        }
    }

    public String checkImage(MultiLayerNetwork model) throws ParameterException {
        try {
            File file = imageToCheck();
            NativeImageLoader loader = new NativeImageLoader(height, width, channels);
            INDArray image = loader.asMatrix(file);
            DataNormalization scale = new ImagePreProcessingScaler(Constants.MIN_RANGE, Constants.MAX_RANGE);
            scale.transform(image);
            return model.output(image).toString().replace("[", "").replace("]", "");
        } catch (IOException e) {
            logger.error("Error during image was being checked.");
            throw new ParameterException(e.getMessage());
        }
    }

    @Override
    public String getLabelList() {
        return labelList.toString().replace("[", "").replace("]", "");
    }

    private File imageToCheck(){
        System.setProperty("java.awt.headless", "false");
        JFileChooser fileChooser = new JFileChooser();
        int ret = fileChooser.showOpenDialog(null);
        if(ret == JFileChooser.APPROVE_OPTION){
            File file = fileChooser.getSelectedFile();
            String fileName = file.getAbsolutePath();
            return new File(fileName);
        } else {
            return null;
        }
    }
}
