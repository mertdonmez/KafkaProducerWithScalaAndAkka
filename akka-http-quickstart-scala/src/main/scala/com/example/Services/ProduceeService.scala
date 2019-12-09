package com.example.Services
import java.util.Properties

import com.example.Models.User
import org.apache.kafka.clients.producer._
import spray.json._
class ProduceeService {


    def writeToKafka(topic: String , user: User): Unit = {
      val props:Properties = new Properties()
      props.put("bootstrap.servers","localhost:9092")
      props.put("key.serializer",
        "org.apache.kafka.common.serialization.StringSerializer")
      props.put("value.serializer",
        "org.apache.kafka.common.serialization.StringSerializer")
      props.put("acks","all")
      val producer = new KafkaProducer[String, String](props)
      val topic = "user_topic1"
      try {
        val record = new ProducerRecord[String, String](topic, System.currentTimeMillis().toString, user.toJson.prettyPrint)
        val metadata = producer.send(record)
        printf(s"sent record(key=%s value=%s) " +
          "meta(partition=%d, offset=%d)\n",
          record.key(), record.value(),
          metadata.get().partition(),
          metadata.get().offset())
        producer.close()
      }catch{
        case e:Exception => e.printStackTrace()
      }finally {
        producer.close()
      }

  }

}
