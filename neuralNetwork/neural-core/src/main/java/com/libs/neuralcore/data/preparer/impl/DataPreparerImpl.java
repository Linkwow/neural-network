package com.libs.neuralcore.data.preparer.impl;

import com.libs.neuralcore.data.preparer.DataPreparer;
import com.libs.neuralcore.entity.DemoData;
import com.libs.neuralcore.util.Constants;
import org.datavec.api.io.labels.ParentPathLabelGenerator;
import org.datavec.api.records.listener.impl.LogRecordListener;
import org.datavec.api.split.FileSplit;
import org.datavec.image.loader.NativeImageLoader;
import org.datavec.image.recordreader.ImageRecordReader;
import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
import org.nd4j.linalg.dataset.DataSet;
import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.*;

public class DataPreparerImpl implements DataPreparer<DataSetIterator> {

    private final Logger logger = LoggerFactory.getLogger(DataPreparerImpl.class);

    private final int height;

    private final int width;

    private final int channels;

    private final int batchSize;

    private final int outputNum;

    private final String samplePath;

    private FileSplit trainingData;

    private FileSplit testingData;

    private final ImageRecordReader imageRecordReader;

    private final DataNormalization scale = new ImagePreProcessingScaler(Constants.MIN_RANGE, Constants.MAX_RANGE);

    private final Random random;

    private boolean trained;

    public DataPreparerImpl(String samplePath, int height, int width, int channels, int batchSize, int outputNum, int seed) {
        this.batchSize = batchSize;
        this.outputNum = outputNum;
        this.samplePath = samplePath;
        this.random = new Random(seed);
        this.height = height;
        this.width = width;
        this.channels = channels;
        this.imageRecordReader = new ImageRecordReader(height, width, channels, new ParentPathLabelGenerator());
    }

    private void setTrainingData(FileSplit trainingData) {
        this.trainingData = trainingData;
    }

    private void setTestingData(FileSplit testingData) {
        this.testingData = testingData;
    }

    @Override
    public DataSetIterator createTrainDataSetIterator() {
        splitFiles();
        createTrainImage();
        trained = true;
        return createIterator(imageRecordReader);
    }

    @Override
    public DataSetIterator createTestDataSetIterator() {
        if (trained) {
            imageRecordReader.reset();
        } else {
            splitFiles();
        }
        createTestImage();
        return createIterator(imageRecordReader);
    }

    @Override
    public List<DemoData> createDemoDataSet() {
        DataSetIterator dataSetIterator = createDemoDataSetIterator();
        List<DemoData> demoDataList = new ArrayList<>();
        for (int i = 0; i < 2; i++) {
            DataSet ds = dataSetIterator.next();
            DemoData demoData = new DemoData();
            String data = ds.toString();
            demoData.setData(Arrays.asList(data.split("\n")));
            List<String> labels = new ArrayList<>(Arrays.asList(dataSetIterator
                    .getLabels()
                    .toString()
                    .replace("[", "")
                    .replace("]", "")
                    .split(", ")));
            demoData.setLabels(changeLabel(labels));
            demoDataList.add(demoData);
        }
        return demoDataList;
    }

    private DataSetIterator createDemoDataSetIterator() {
        splitFiles();
        DataSetIterator dataSetIterator = new RecordReaderDataSetIterator(createDemoImage(), Constants.BATCH_SIZE_FOR_DEMO, Constants.LABEL_INDEX, outputNum);
        scale.fit(dataSetIterator);
        dataSetIterator.setPreProcessor(scale);
        return dataSetIterator;
    }

    private void createTrainImage() {
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

    private ImageRecordReader createDemoImage() {
        ImageRecordReader imageRecordReader = new ImageRecordReader(height, width, channels, new ParentPathLabelGenerator());
        try {
            imageRecordReader.initialize(trainingData);
            imageRecordReader.setListeners(new LogRecordListener());
        } catch (IOException e) {
            logger.error("Error during initialize createDemoDataSet data");
            throw new RuntimeException(e);
        }
        return imageRecordReader;
    }

    private DataSetIterator createIterator(ImageRecordReader imageRecordReader) {
        DataSetIterator dataSetIterator = new RecordReaderDataSetIterator(imageRecordReader, batchSize, Constants.LABEL_INDEX, outputNum);
        scale.fit(dataSetIterator);
        dataSetIterator.setPreProcessor(scale);
        return dataSetIterator;
    }

    private void splitFiles() {
        File[] directories = Path.of(samplePath).toFile().listFiles();
        for (File pathToDirectory : Optional.ofNullable(directories).orElseThrow()) {
            if (pathToDirectory.isDirectory() && pathToDirectory.getPath().contains("training"))
                setTrainingData(new FileSplit(pathToDirectory, NativeImageLoader.ALLOWED_FORMATS, random));
            else if (pathToDirectory.isDirectory() && pathToDirectory.getPath().contains("testing"))
                setTestingData(new FileSplit(pathToDirectory, NativeImageLoader.ALLOWED_FORMATS, random));
        }
    }
    //fixme : use here iterator, not raw collections methods
    private String changeLabel(List<String> labels) {
        StringBuilder label = new StringBuilder("         ");
        int staticCharPosition = label.length() - 1;
        for (int i = 0; i < labels.size(); i++) {
            label.append(labels.get(i));
            labels.remove(i);
            labels.add(i, label.toString());
            label.delete(staticCharPosition + 1, label.length());
        }
        return labels.toString();
    }
}