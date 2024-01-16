package com.noelvu.kafkaproducer.producer;

import com.noelvu.kafkaproducer.api.ProduceMessageRequest;
import com.noelvu.kafkaproducer.api.ProduceMessageResponse;
import com.noelvu.kafkaproducer.model.MyMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

@Slf4j
@Component
@RequiredArgsConstructor
public class MyTopicProducer {

	private final KafkaTemplate<String, SpecificRecord> kafkaTemplate;

	@Value("${kafka.topics.myTopic.name}")
	private String myTopic;

	public ProduceMessageResponse produceMessage(ProduceMessageRequest request, String sender) {
		MyMessage event = MyMessage.newBuilder()
				.setContent(String.format("%s send a message: %s", sender, request.message()))
				.build();
		try {
			var response = kafkaTemplate.send(myTopic, event).get();
			var offset = response.getRecordMetadata().offset();
			var timestamp = response.getRecordMetadata().timestamp();
			var partition = response.getRecordMetadata().partition();
			return ProduceMessageResponse.builder()
					.topic(myTopic)
					.message(event.getContent())
					.offset(String.valueOf(offset))
					.partition(String.valueOf(partition))
					.sendAt(Instant.ofEpochMilli(timestamp).atZone(ZoneId.systemDefault()).format(DateTimeFormatter.ISO_DATE_TIME))
					.build();
		} catch (Exception e){
			log.error("Failed to send event to kafka", e);
			return ProduceMessageResponse.builder()
					.topic(myTopic)
					.message(event.getContent())
					.offset("null")
					.partition("null")
					.sendAt("null")
					.build();
		}

	}

}
