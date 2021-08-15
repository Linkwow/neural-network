package com.libs.naumenko.neuralcore;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.io.IOException;
import java.util.concurrent.ExecutionException;

@SpringBootTest
@ActiveProfiles("test")
class NeuralCoreApplicationTests {

    @Autowired
    public NeuralCoreService neuralCoreService;

    @Test
    void contextLoads() throws IOException, ExecutionException, InterruptedException {
       neuralCoreService.loadSampleFromSource();
    }

    @SpringBootApplication
    static class TestConfiguration {

    }
}
