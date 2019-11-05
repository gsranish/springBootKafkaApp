package com.gsr.springBootKafkaApp.store;

import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageStore {
	
	private List<String> list=new ArrayList<String>();
	
	public void put(String message) {
		// write logic to store data in database
		list.add(message);
	}
	public String getAll() {
		// to get message as a list
		return list.toString();
	}
}
