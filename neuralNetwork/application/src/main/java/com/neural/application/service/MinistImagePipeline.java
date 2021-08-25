//package com.neural.application.service;
//
//
//import org.datavec.api.io.labels.ParentPathLabelGenerator;
//import org.datavec.api.records.listener.impl.LogRecordListener;
//import org.datavec.api.split.FileSplit;
//import org.datavec.image.loader.NativeImageLoader;
//import org.datavec.image.recordreader.ImageRecordReader;
//import org.deeplearning4j.datasets.datavec.RecordReaderDataSetIterator;
//
//import org.deeplearning4j.nn.api.OptimizationAlgorithm;
//import org.deeplearning4j.nn.conf.MultiLayerConfiguration;
//import org.deeplearning4j.nn.conf.NeuralNetConfiguration;
//import org.deeplearning4j.nn.conf.inputs.InputType;
//import org.deeplearning4j.nn.conf.layers.DenseLayer;
//import org.deeplearning4j.nn.conf.layers.OutputLayer;
//import org.deeplearning4j.nn.multilayer.MultiLayerNetwork;
//import org.deeplearning4j.nn.weights.WeightInit;
//import org.deeplearning4j.optimize.listeners.ScoreIterationListener;
//import org.deeplearning4j.util.ModelSerializer;
//import org.nd4j.evaluation.classification.Evaluation;
//import org.nd4j.linalg.activations.impl.ActivationReLU;
//import org.nd4j.linalg.activations.impl.ActivationSoftmax;
//import org.nd4j.linalg.api.ndarray.INDArray;
//import org.nd4j.linalg.dataset.DataSet;
//import org.nd4j.linalg.dataset.api.iterator.DataSetIterator;
//import org.nd4j.linalg.dataset.api.preprocessor.DataNormalization;
//import org.nd4j.linalg.dataset.api.preprocessor.ImagePreProcessingScaler;
//import org.nd4j.linalg.learning.config.Adam;
//import org.nd4j.linalg.learning.config.Nesterovs;
//import org.nd4j.linalg.lossfunctions.LossFunctions;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//
//import javax.swing.*;
//import java.io.File;
//import java.io.IOException;
//import java.util.Arrays;
//import java.util.List;
//import java.util.Random;
//
//
//public class MinistImagePipeline {
//
//    private static Logger logger = LoggerFactory.getLogger(MinistImagePipeline.class);
//
//    public static String fileToChoose(){
//        JFileChooser fc = new JFileChooser();
//        int ret = fc.showOpenDialog(null);
//        if(ret == JFileChooser.APPROVE_OPTION){
//            File file = fc.getSelectedFile();
//            String fileName = file.getAbsolutePath();
//            return fileName;
//        } else {
//            return null;
//        }
//    }
//
//    public static void main(String[] args) throws IOException {
//
//        int height = 28;
//        int width = 28;
//        int channels = 1;
//        int batchSize = 128;
//        int outputNum = 10;
//        int rngSeed = 123;
//        int numEpochs = 1;
//
//        Random randomGen = new Random(rngSeed);
//
//        File trainData = new File("E:\\mnist_png\\training");
//        File testData = new File("E:\\mnist_png\\testing");
//        FileSplit train = new FileSplit(trainData, NativeImageLoader.ALLOWED_FORMATS, randomGen);
//        FileSplit test = new FileSplit(testData, NativeImageLoader.ALLOWED_FORMATS, randomGen);
//        ParentPathLabelGenerator labelMaker = new ParentPathLabelGenerator();
//        ImageRecordReader recordReader = new ImageRecordReader(height, width, channels, labelMaker);
//        recordReader.initialize(train);
//        //recordReader.setListeners(new LogRecordListener());
//
//        DataSetIterator dataIter = new RecordReaderDataSetIterator(recordReader, 1, 1, outputNum);
//
//        DataNormalization scaler = new ImagePreProcessingScaler(0, 1);
//        scaler.fit(dataIter);
//        dataIter.setPreProcessor(scaler);
//
//        //todo create method for demonstration
//        for (int i = 1; i < 11; i++) {
//            DataSet ds = dataIter.next();
//            System.out.println(ds);
//            //System.out.println(dataIter.getLabels());
//        }
//
//
//
//        logger.info("Build Model");
//
//        MultiLayerConfiguration conf = new NeuralNetConfiguration.Builder()
//                .seed(rngSeed)
//                .optimizationAlgo(OptimizationAlgorithm.STOCHASTIC_GRADIENT_DESCENT)
//                .updater(new Adam(0.006))
//                .updater(new Nesterovs(0.9))
//                .l2(1e-4)
//                .list()
//                .layer(0, new DenseLayer.Builder()
//                        .nIn(height * width)
//                        .nOut(100)
//                        .activation(new ActivationReLU())
//                        .weightInit(WeightInit.XAVIER)
//                        .build())
//                .layer(1, new OutputLayer.Builder(LossFunctions.LossFunction.NEGATIVELOGLIKELIHOOD)
//                        .nIn(100)
//                        .nOut(10)
//                        .activation(new ActivationSoftmax())
//                        .weightInit(WeightInit.XAVIER)
//                        .build())
//                .setInputType(InputType.convolutional(height, width, channels))
//                .build();
//
//        MultiLayerNetwork model = new MultiLayerNetwork(conf);
//        model.init();
//
//
//        logger.info("Train model");
//        model.pretrain(dataIter, numEpochs);
//        model.fit(dataIter, numEpochs);
//        model.setListeners(new ScoreIterationListener(10));
//
//
//        logger.info("Evaluate model");
//        recordReader.reset();
//        recordReader.initialize(test);
//        DataSetIterator testIter = new RecordReaderDataSetIterator(recordReader, batchSize, 1, outputNum);
//        scaler.fit(testIter);
//        testIter.setPreProcessor(scaler);
//
//        Evaluation eval = new Evaluation(outputNum);
//
//
//        while (testIter.hasNext()) {
//            DataSet next = testIter.next();
//            INDArray output = model.output(next.getFeatures());
//            eval.eval(next.getLabels(), output);
//        }
//
//        logger.info(eval.stats());
//
//
//
//        logger.info("save model");
//        File locationToSave = new File("trained_mnist_model.zip");
//        boolean saveUpdaters = false;
//        ModelSerializer.writeModel(model, locationToSave, saveUpdaters);
//        //load model
//        model = ModelSerializer.restoreMultiLayerNetwork(locationToSave);
//
//        //test model
//        List<Integer> labelList = Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8, 9);
//
//        String fileChoose = fileToChoose();
//        File file = new File(fileChoose);
//        NativeImageLoader loader = new NativeImageLoader(height, width, channels);
//        INDArray image = loader.asMatrix(file);
//        DataNormalization scaler2 = new ImagePreProcessingScaler(0,1);
//        scaler.transform(image);
//
//        INDArray output = model.output(image);
//        logger.info(output.toString());
//        logger.info(labelList.toString());
//    }
//}
