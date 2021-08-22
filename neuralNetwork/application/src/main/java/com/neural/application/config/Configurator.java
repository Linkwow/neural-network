package com.neural.application.config;

import com.libs.neuralcore.api.NeuralCoreAPI;
import com.neural.application.service.NeuralService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@ComponentScan("com.libs.neuralcore")
public class Configurator {

    @Bean
    public NeuralCoreAPI initNeuralCoreAPI(){
        return new NeuralCoreAPI();
    }

    @Bean
    public NeuralService initNeuralService(){
        return new NeuralService();
    }
}
