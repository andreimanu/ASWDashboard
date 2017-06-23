package es.uniovi.asw.com.example;

import java.util.Arrays;

import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TheKafkaApplication {

	public static void main(String[] args) {
		// SpringApplication.run(TheKafkaApplication.class, args);

		KafkaConsumer kfc = new KafkaConsumer();
		kfc.SubscribeTo(Arrays.asList("votedProposal", "votedComment", "createdProposal", "createdComment", "deletedProposal"));
		/*kfc.Subscribe("votedProposal");
		kfc.Subscribe("votedComment");
		kfc.Subscribe("createdProposal");
		kfc.Subscribe("createdComment");
		kfc.Subscribe("deletedProposal");*/
		kfc.Read();
	}
}
