11. Spring Boot Apache Kafka Integration:-- Apache kafka is used for 

=>Apache kafka supports AMQP for large data transfer for MQ applications over network.
=>Supports Real-time Data Streaming. It means read continuous and large data from external source like Float Files, Database, networks, etc…

=>It supports data reading/writing from
     a. Databases
     b. Flat File System
     c. Applications (Data Streams).

=>Kafka supports integration with any type of application (language Independent + Plugin required, default JAVA).
=>Kafka supports basic concepts of MQ like
    1. Data Streaming
    2. Web Activities
    3. Log-Aggregations
    4. Command-Components

=>Kafka follows below concept even,

a.>Kafka uses Message Broker also called as Broker Server which supports MQ operations.
b.>Kafka supports Load Balancing for Broker Software to avoid more traffic, i.e. called as Kafka cluster. In general cluster is a group of Broker servers (1 to n).
c.>Kafka supports only Topics (Pub/Sub Model) and it is only default also.
d.>Kafka Cluster Auto-Scaling is done by Bootstrap server (AKA Zookeeper). It behaves as R&D Server.

e.>Data is sent to Topic in <K, V> format. K=TopicName (Destination), V=Data.
f>All these Broker instances must be Registered with Registory and Discovery (R&D) Server. Kafka comes with default “Zookeeper R&D Server”.
g.> This complete Kafka Software is called as Kafka EcoSystem (Kafka Eco-System = Kafka Cluster + Bootstrap Server).

h>. Data Partitions concept is used by kafka to send large data.
i.>Message Brokers will persist the message (save into their memory) to avoid data lose in case of consumer non-available or broker is down.
j.>Kafka works as Protocol independent i.e. works for TCP, FTP, SMTP, HTTP… etc)

Kafka EcoSystem Diagram:--
 

Execution Flow:--
=>Producer Application should get Message Broker details from R & D Server (zookeeper) known as bootstrap-server).
=>Producer gets unique-id (InstanceId) of Message Broker server and sends message to Broker.Producer use KafkaTemplate<K, V> to send data to one Broker server.
=>Message Broker will send this message to one or multiple consumers.
=>Producer sends data <k, V> format in Serialized (Converting to binary/Characters formats). Here K=Destination (Topic name) and V= Message.

=>Every Message will be partitioned into Multiple parts in Topic (Destination) to avoid large data sending, by making into small and equal parts (some time size may vary).
=>Broker reads all partitions data and creates its replica (Clone/Mirror obj) to send message to multiple consumers based on Topic and Group-Id.
=>At Consumer side Deserialization must be applied on K, V to read data. Consumer should also be linked with bootstrap-server to know its broker.

 

=>Partitions are used to breakdown large message into multiple parts and send same to multiple brokers to make data destination in parallel.

Message Replica:-- It creates multiple copies to one message to publish one message to multiple Consumers.

Kafka Producer and Consumer Setup Details:--

=>For Producer Application we should provide details in application.properties (or .yml).
=>Those are
bootstrap-servers=localhost:9092
key-serializer=StringSerializer
value-serializer=StringSerializer

=>By using this Spring Boot creates instance of “KafkaTemplate<K, V>” then we can call send(k, v) method which will send data to Consumer.
    
 ->Here : K=Topic Name,  V= Data/Message

=>For Consumer Application we should provide details in application.properties (or .yml)
=>Those are
bootstrap-servers=localhost:9092
key-deserializer=StringDeserializer
value-deserializer=StringDeserializer
group-id=MyGroupId

=>By using this Spring Boot configures the Consumer application, which must be implemented using : @KafkaListener(topics=”—“, groupId=”—“)

Kafka Download and Setup:--
Apache Kafka comes with Unix based software called as Scala 2.x

=>Download link (https://kafka.apache.org/downloads)
=>Choose one mirror=>Extract once (.tgz -> .tar)
=>Extract one more time (.tar -> folder)
=>Open folder and create --.bat file for bootstrap Server (Zookeeper).

*** bat files in kafka to be created***
1>Server.bat
=>Starts Kafka Server (Message Broker)
.\bin\windows\zookeeper-server-start.bat  .\config\zookeeper.properties

2>cluster.bat
=>Starts Zoopeer with Kafka Cluster design
.\bin\windows\kafka-server-start.bat  .\config\server.properties
 
Coding Steps:--
Step#1:- Choose spring apache kafka Dependency while Creating project (Which gives Integration of Spring with Kafka).

groupId    : com.app
artifactId  : SpringBootKafkaApp
version     : 1.0

Kafka Dependency:--
<dependency>
    <groupId>org.springframework.kafka</groupId>
    <artifactId>spring-kafka</artifactId>
</dependency>
#17. Folder Structure of Kafka:--
 

Step#2:- add key= value pairs in application (.properties/ .yml) file.
application.properties:--
server.port: 9988
#Producer properties
my-app-topicname: sampletopic
spring.kafka.producer.bootstrap-servers: localhost:9092
spring.kafka.producer.key-serializer: org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer: org.apache.kafka.common.serialization.StringSerializer

#Consumer properties
spring.kafka.consumer.bootstrap-servers: localhost:9092
spring.kafka.consumer.key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
spring.kafka.consumer.group-id: group-id
properties.yml:--
server:
    port: 9988
my:
    app:
        topicname: sampletopic
spring:
    kafka:
        producer:
            bootstrap-servers: localhost:9092
            key-serializer: org.apache.kafka.common.serialization.StringSerializer
            value-serializer: org.apache.kafka.common.serialization.StringSerializer
   
       consumer:
           bootstrap-servers: localhost:9092
           key-deserializer: org.apache.kafka.common.serialization.StringDeserializer
           value-deserializer: org.apache.kafka.common.serialization.StringDeserializer
           group-id: groupId

Step#3:- Define MessageStorage class
package com.app.store;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Component;

@Component
public class MessageStorage {
	private List<String> list= new ArrayList<String>();

	public void put(String message) {
	      list.add(message);  //Write the logic to store data in database
	}
	public String getAll() {
	     return list.toString();
	}
}
Step#4:- Define Consumer class
package com.app.consumer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;
import com.app.store.MessageStorage;

@Component
public class Consumer {
           @Autowired
	private MessageStorage storage;

	@KafkaListener (topics="${my.app.topicname}", groupId="groupId")
	public void consume (String message) {
		storage.put(message);
	} }
Step#5:- Define Producer code
package com.app.producer;
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
		template.send(topic, message);
	}
} 
Step#6:- Define KafkaRestConstroller class
package com.app.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.app.producer.Producer;
import com.app.store.MessageStorage;

@RestController
public class KafkaRestController {

	@Autowired
	private Producer producer;
	
           @Autowired
	private MessageStorage storage;

	@RequestMapping("/send")
	public String readInMessage(@RequestParam String message)
	{
		producer.sendMessage(message);
		return "message sent!!";
	}
	@RequestMapping("/view")
	public String viewMessage() {
		return storage.getAll();
	}
}

Coding order:--
1. application.properties:--
2. MessageStorage.java
3. ConsumerService.java
4. ProducerRestConstroller.java
5. KafkaRestController.java
NOTE:-- Use KafkaTemplate <K, V> at producer application to send message(V) to given Topic (K).

NOTE:-- Use @KafkaListener (topics=”….”, groupId=”….”) at consumer side to read message (V) using topic (K) with groupId(G).

 

Execution Orde:--
1>Start the Zookeeper Server
2>Start kafks-server (Cluster)
3>Start  Application Starter class (Provider, Consumer)

#1:- Start the Zookeeper Server 

#2:-- Start the kafka-Server (Cluster)
 

3#:-- ***Run Starter class and enter URLs:--
1. http://localhost:9988/kafka/send?message=OK
 

2. http://localhost:9988/kafka/view
