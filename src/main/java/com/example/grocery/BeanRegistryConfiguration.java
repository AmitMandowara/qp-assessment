package com.example.grocery;

import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanRegistryConfiguration {

    @Bean("modelMapper")
    ModelMapper getModelMapper() {
		return new ModelMapper();
	}
}
