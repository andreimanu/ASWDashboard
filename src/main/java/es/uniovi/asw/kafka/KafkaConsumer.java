package es.uniovi.asw.kafka;

import java.util.Arrays;
import java.util.List;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import es.uniovi.asw.dao.ProposalDao;

public class KafkaConsumer {
	private static org.apache.kafka.clients.consumer.KafkaConsumer<String, String> kfc;

	public KafkaConsumer() {
		if (kfc == null) {
			Properties prop = new Properties();
			prop.put("bootstrap.servers", "localhost:9092");
			prop.put("group.id", "test"); 
			prop.put("enable.auto.commit", "true");
			prop.put("auto.commit.interval.ms", "1000");
			prop.put("session.timeout.ms", "30000");
			prop.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			prop.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
			kfc = new org.apache.kafka.clients.consumer.KafkaConsumer<String, String>(prop);
		}
	}

	public void SubscribeTo(List<String> topic) {
		kfc.subscribe(topic);
	}
	public void Subscribe(String topic) {
		kfc.subscribe(Arrays.asList(topic));
	}

	public void Read() {
		while (true) {
			try{
				ConsumerRecords<String, String> cr = kfc.poll(Long.MAX_VALUE);
				for (ConsumerRecord<String, String> record : cr) {
					System.out.println(record.key() + " : " + record.value());
					new ProposalDao();
					ProposalDao.NewID = Integer.parseInt(record.value());
					ProposalDao.Refresh = true;
				}
			}
			catch(Exception e) { }
		}
	}
}
