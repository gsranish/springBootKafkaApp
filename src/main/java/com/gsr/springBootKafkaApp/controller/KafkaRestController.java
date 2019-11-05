package com.gsr.springBootKafkaApp.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.gsr.springBootKafkaApp.producer.Producer;
import com.gsr.springBootKafkaApp.store.MessageStore;

@RestController
@RequestMapping("/kafka")
public class KafkaRestController {

	@Autowired
	private Producer producer;
	
	@Autowired
	private MessageStore store;
	
	@RequestMapping("/send")
	public String readInMessage(@RequestParam String message) {
		producer.sendMessage(message);
		return "message sent!!";	
	}
	@RequestMapping("/view")
	public String viewMessage() {
		return store.getAll();
	}
	
}
