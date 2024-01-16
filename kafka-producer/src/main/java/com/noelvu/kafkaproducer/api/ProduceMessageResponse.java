package com.noelvu.kafkaproducer.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude
public record ProduceMessageResponse(String message, String sendAt, String topic, String partition, String offset) {}
