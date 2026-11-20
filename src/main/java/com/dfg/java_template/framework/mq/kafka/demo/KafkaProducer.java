//package com.dfg.java_template.framework.mq.kafka.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.kafka.core.KafkaTemplate;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//
//@Service
//public class KafkaProducer {
//
//    @Resource
//    KafkaTemplate<String, String> kafkaTemplate;
//
//    private static final String TOPIC = "test-topic";
//
//    public void sendMessage(String message) {
//        kafkaTemplate.send(TOPIC, message);
//        System.out.println("消息已发送: " + message);
//    }
//}
