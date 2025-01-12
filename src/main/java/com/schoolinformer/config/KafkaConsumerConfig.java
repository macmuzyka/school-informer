package com.schoolinformer.config;

import com.schoolinformer.deserializer.FeedbackDTODeserializer;
import com.schoolinformer.model.dto.FeedbackDTO;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConsumerConfig {
    @Value(value = "${spring.kafka.bootstrap-servers}")
    private String bootstrapAddress;

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, FeedbackDTO> feedbackListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, FeedbackDTO> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(defaultKafkaConsumerFactoryWithBasicConfig("feedback", FeedbackDTODeserializer.class));
        return factory;
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> applicationVersionFetchListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(defaultKafkaConsumerFactoryWithBasicConfig("application-version", StringDeserializer.class));
        return factory;
    }

    @Bean
    public <D> ConsumerFactory<String, Object> defaultKafkaConsumerFactoryWithBasicConfig(String groupId, Class<D> deserializer) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapAddress);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, groupId);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, deserializer);
        return new DefaultKafkaConsumerFactory<>(props);
    }
}