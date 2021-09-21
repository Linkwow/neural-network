package com.neural.application.controller;

import com.libs.neuralcore.exceptions.ParameterException;
import com.neural.application.service.NeuralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("/neural-network")
public class NeuralController {

    private NeuralService neuralService;

    @Autowired
    public void setNeuralService(NeuralService neuralService) {
        this.neuralService = neuralService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/download-from-source")
    public ModelAndView downloadSample() {
        ModelAndView modelAndView = new ModelAndView("download");
        try {
            String result = neuralService.downloadSampleFromSource();
            modelAndView.addObject("result", result);
        } catch (ParameterException e) {
            throw new RuntimeException(e.getMessage());
        }
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/demo")
    public ModelAndView demoImage() {
        ModelAndView modelAndView = new ModelAndView("demo");
        Map<String, Object> data = new HashMap<>();
        data.put("data", neuralService.demonstrateModel());
        modelAndView.addAllObjects(data);
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/train-model")
    public ModelAndView trainModel() throws ParameterException {
        File model;
        try {
           model = neuralService.trainModel();
        } catch (ParameterException e) {
            throw new ParameterException(e.getMessage());
        }
        ModelAndView modelAndView = new ModelAndView("train");
        modelAndView.addObject("result", model.getAbsolutePath());
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/evaluate-model")
    public ModelAndView evaluateModel() throws ParameterException {
        ModelAndView modelAndView = new ModelAndView("evaluate");
        try {
            modelAndView.addObject("result", neuralService.evaluateModel());
        } catch (ParameterException e) {
            throw new ParameterException(e.getMessage());
        }
        return modelAndView;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/check-model")
    public ModelAndView checkModel() throws ParameterException {
        ModelAndView modelAndView = new ModelAndView("check");
        try {
            modelAndView.addObject("results", neuralService.checkModel());
        } catch (ParameterException e) {
            throw new ParameterException(e.getMessage());
        }
        return modelAndView;
    }
}
