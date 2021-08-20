package com.libs.neuralcore.data.praparer.impl;

import com.libs.neuralcore.data.praparer.DataPreparer;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class DataPreparerImpl implements DataPreparer<DataSetIterator> {

    private final Logger logger = LoggerFactory.getLogger(DataPreparerImpl.class);

    private final int LABEL_INDEX = 1;

    private final int MIN_RANGE = 0;

    private final int MAX_RANGE = 1;

    private File[] samplePath;

    private FileSplit trainingData;

    private FileSplit testingData;

    private ImageRecordReader imageRecordReader;

    private final int height;

    private final int width;

    private final int channels;

    private final int batchSize;

    private final int outputNum;

    private final DataNormalization scale = new ImagePreProcessingScaler(MIN_RANGE, MAX_RANGE);

    //private Random random = new Random();

    public void setSamplePath(String samplePath) {
        this.samplePath = Path.of(samplePath).toFile().listFiles();
    }

    public void setTrainingData(FileSplit trainingData) {
        this.trainingData = trainingData;
    }

    public void setTestingData(FileSplit testingData) {
        this.testingData = testingData;
    }

    public DataPreparerImpl(int height, int width, int channels, int batchSize, int outputNum) {
        this.height = height;
        this.width = width;
        this.channels = channels;
        this.batchSize = batchSize;
        this.outputNum = outputNum;
    }

    @Override
    public DataSetIterator createTrainDataSet() {
        splitFiles();
        createTrainImage();
        DataSetIterator trainDataSet = new RecordReaderDataSetIterator(imageRecordReader, batchSize, LABEL_INDEX, outputNum);
        scale.fit(trainDataSet);
        trainDataSet.setPreProcessor(scale);
        return trainDataSet;
    }

    @Override
    public DataSetIterator createTestDataSet() {
        imageRecordReader.reset();
        createTestImage();
        DataSetIterator testDataSet = new RecordReaderDataSetIterator(imageRecordReader, batchSize, LABEL_INDEX, outputNum);
        scale.fit(testDataSet);
        testDataSet.setPreProcessor(scale);
        return testDataSet;
    }

    private void createTrainImage() {
        imageRecordReader = new ImageRecordReader(height, width, channels, new ParentPathLabelGenerator());
        try {
            imageRecordReader.initialize(trainingData);
        } catch (IOException e) {
            logger.error("Error during initialize image data");
            throw new RuntimeException(e);
        }
    }

    private void createTestImage() {
        try {
            imageRecordReader.initialize(testingData);
        } catch (IOException e) {
            logger.error("Error during initialize image data");
            throw new RuntimeException(e);
        }
    }

    private void splitFiles() {
        for (File pathToDirectory : samplePath) {
            if (pathToDirectory.isDirectory() && pathToDirectory.getPath().contains("training"))
                setTrainingData(new FileSplit(pathToDirectory, NativeImageLoader.ALLOWED_FORMATS));
            else if (pathToDirectory.isDirectory() && pathToDirectory.getPath().contains("testing"))
                setTestingData(new FileSplit(pathToDirectory, NativeImageLoader.ALLOWED_FORMATS));
        }
    }
}
