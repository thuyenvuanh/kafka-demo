package com.noelvu.kafkaproducer.api;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;

@Builder
@JsonInclude
public record ProduceMessageRequest(String message) {
}
