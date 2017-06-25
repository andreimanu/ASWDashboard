package es.uniovi.asw.kafka;

import java.util.concurrent.CountDownLatch;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;

import es.uniovi.asw.MainController;

public class Listener {
	public final CountDownLatch countDownLatch1 = new CountDownLatch(1);
	 
	@KafkaListener(id = "vpListener", topics = "votedProposal", group = "test")
	public void listen(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE VOTED PROPOSAL \n -------------------");
	}

	@KafkaListener(id = "vcListener", topics = "votedComment", group = "test")
	public void listenVotedComment(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE VOTED COMMENT \n -------------------");
	}
	
	@KafkaListener(id = "ccListener", topics = "createdComment", group = "test")
	public void listenCreatedComment(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE CREATED COMMENT \n -------------------");
	}
	
	@KafkaListener(id = "cpListener", topics = "createdProposal", group = "test")
	public void listenCreatedProposal(ConsumerRecord<?, ?> record) {
		MainController.refresh = true;
		System.out.println("------------------\n RECEIVED MESSAGE CREATED PROPOSAL \n -------------------");
	}
}