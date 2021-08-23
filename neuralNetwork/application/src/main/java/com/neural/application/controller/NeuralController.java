package com.neural.application.controller;

import com.neural.application.service.NeuralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

@Controller
@RequestMapping("neural-network")
public class NeuralController {

    private NeuralService neuralService;

    @Autowired
    public void setNeuralService(NeuralService neuralService) {
        this.neuralService = neuralService;
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/demo")
    public ModelAndView demoImage() {
        ModelAndView modelAndView = new ModelAndView("demo");
        Map<String, Object>
                data = new HashMap<>(),
                labels = new HashMap<>();
        data.put("data", neuralService.getDataSets());
        labels.put("labels", neuralService.getLabels());
        modelAndView.addAllObjects(data);
        modelAndView.addAllObjects(labels);
        return modelAndView;
    }
}
