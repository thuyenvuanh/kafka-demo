package com.noelvu.kafkaproducer.controller;

import com.noelvu.kafkaproducer.api.ProduceMessageRequest;
import com.noelvu.kafkaproducer.api.ProduceMessageResponse;
import com.noelvu.kafkaproducer.producer.MyTopicProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@Validated
@RestController
@RequiredArgsConstructor
@RequestMapping("/produce")
public class ProducerController {

	private final MyTopicProducer myTopicProducer;

	@PostMapping
	public ResponseEntity<ProduceMessageResponse> produceEvent(
			@RequestHeader(HttpHeaders.AUTHORIZATION) String sender,
			@RequestBody ProduceMessageRequest request
			) {
		return new ResponseEntity<>(myTopicProducer.produceMessage(request, sender), HttpStatus.CREATED);
	}

}
