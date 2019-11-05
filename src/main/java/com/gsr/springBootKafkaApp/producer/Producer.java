package com.gsr.springBootKafkaApp.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Component
public class Producer {
	
	@Value("${my.app.topicname}")
	private String topic;
	
	@Autowired
	private KafkaTemplate<String, String> template;
	
	public void sendMessage(String message) {
		template.send(topic,message);
	}
	
}
