package com.neural.application.config;

import com.libs.neuralcore.api.NeuralCoreAPI;
import com.neural.application.service.NeuralService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan(basePackages = "com.libs.neuralcore.config")
public class Configurator {

    @Value("${outputNum}")
    private int outputNum;

    @Bean
    public NeuralCoreAPI initNeuralCoreAPI() {
        return new NeuralCoreAPI();
    }

    @Bean
    public NeuralService initNeuralService() {
        return new NeuralService();
    }
}