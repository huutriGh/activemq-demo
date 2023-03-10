package com.aptech.activemqdemo;

import java.io.BufferedReader;
import java.io.InputStreamReader;

import org.apache.qpid.jms.JmsConnectionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import jakarta.jms.Connection;
import jakarta.jms.ConnectionFactory;
import jakarta.jms.Destination;
import jakarta.jms.MessageProducer;
import jakarta.jms.Session;
import jakarta.jms.TextMessage;


@SpringBootApplication
public class ActivemqDemoApplication implements CommandLineRunner {

	Logger  logger = LoggerFactory.getLogger(ActivemqDemoApplication.class);
	public static void main(String[] args)  {
		SpringApplication.run(ActivemqDemoApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		ConnectionFactory connectionFactory = new JmsConnectionFactory("amqp://huutri-pc:56722");
		Connection connection = connectionFactory.createConnection("admin", "admin");
        connection.start();
		Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
		Destination destination = session.createQueue("jms-queue");
		MessageProducer producer = session.createProducer(destination);
		// try (BufferedReader br = new BufferedReader(new InputStreamReader(System.in));) {
        //     String response;
        //     do {
        //         System.out.print("Enter message: ");
        //         response = br.readLine().trim();
        //         TextMessage msg = session.createTextMessage(response);
        //         producer.send(msg);
        //     } while (!response.equalsIgnoreCase("close"));
        // }

		for (int i = 1; i <= 1000; i++) {
			TextMessage msg = session.createTextMessage("Hello Word " + i) ;
			producer.send(msg);
		}
		producer.send(session.createTextMessage("close"));
		connection.close();
 


		
	}

}
