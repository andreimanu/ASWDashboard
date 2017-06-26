package es.uniovi.asw.dashboard.kafka;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import es.uniovi.asw.dashboard.MainController;

public class Listener {
	public final CountDownLatch countDownLatch1 = new CountDownLatch(1);
	 
	@KafkaListener(id = "vpListener", topics = "votedProposal", group = "test")
	public void listen(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE VOTED PROPOSAL \n -------------------");
		System.out.println("Proposal ID: " + record.value().toString().split(":")[0] + ", from user: " 
				+ record.value().toString().split(":")[1] + " and is: " +
				("1".equals(record.value().toString().split(":")[2])? "positive":"negative"));
	}

	@KafkaListener(id = "vcListener", topics = "votedComment", group = "test")
	public void listenVotedComment(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE VOTED COMMENT \n -------------------");
		System.out.println("Comment ID: " + record.value().toString().split(":")[0] + ", from user: " 
				+ record.value().toString().split(":")[1] + " and is: " +
				("1".equals(record.value().toString().split(":")[2])? "positive":"negative"));
	}
	
	@KafkaListener(id = "ccListener", topics = "createdComment", group = "test")
	public void listenCreatedComment(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE CREATED COMMENT \n -------------------");
		System.out.println("new Comment ID: " + record.value().toString().split(":")[0] + ", from user: " 
				+ record.value().toString().split(":")[1]);
	}
	
	@KafkaListener(id = "cpListener", topics = "createdProposal", group = "test")
	public void listenCreatedProposal(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE CREATED PROPOSAL \n -------------------");
		System.out.println("new Proposal ID: " + record.value().toString().split(":")[0] + ", from user: " 
				+ record.value().toString().split(":")[1]);
	}
}