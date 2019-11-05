package com.gsr.springBootKafkaApp.consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import com.gsr.springBootKafkaApp.store.MessageStore;

@Component
public class Consumer {
	
	@Autowired
	private MessageStore store;

	@KafkaListener(topics = "${my.app.topicname}",groupId = "group-id")
	public void consume(String message) {
		store.put(message);
	}
	
	
}
