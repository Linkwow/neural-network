package com.neural.application;

import com.neural.application.service.NeuralService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.ComponentScan;

@SpringBootTest
@ComponentScan("com.libs.neuralcore")
class ApplicationTests {

    private NeuralService neuralService;

    @Autowired
    public void setNeuralService(NeuralService neuralService) {
        this.neuralService = neuralService;
    }

    @Test
    void contextLoads() {

    }
}