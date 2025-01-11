package com.schoolinformer.config;

import com.schoolinformer.model.dto.ApplicationValidityDTO;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.Serializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.support.serializer.JsonSerializer;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Configuration
public class KafkaTemplatesProducerConfig {
    @Value("${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ProducerFactory<String, Map<String, List<String>>> roadmapProducerFactory() {
        return new DefaultKafkaProducerFactory<>(provideBasicKafkaProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, Map<String, List<String>>> roadmapKafkaTemplate() {
        return new KafkaTemplate<>(roadmapProducerFactory());
    }

    @Bean
    public ProducerFactory<String, ApplicationValidityDTO> applicationVersionValidityFactory() {
        return new DefaultKafkaProducerFactory<>(provideBasicKafkaProducerConfig());
    }

    @Bean
    public KafkaTemplate<String, ApplicationValidityDTO> applicationValidityKafkaTemplate() {
        return new KafkaTemplate<>(applicationVersionValidityFactory());
    }

    private Map<String, Object> provideBasicKafkaProducerConfig() {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return configProps;
    }
}
