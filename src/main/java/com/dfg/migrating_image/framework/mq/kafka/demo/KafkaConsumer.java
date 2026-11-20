//package com.dfg.java_template.framework.mq.kafka.demo;
//
//import org.springframework.kafka.annotation.KafkaListener;
//import org.springframework.kafka.support.Acknowledgment;
//import org.springframework.stereotype.Service;
//
//@Service
//public class KafkaConsumer {
//
//    @KafkaListener(topics = "test-topic", groupId = "demo-group")
//    public void listen(String message, Acknowledgment ack) {
//        System.out.println("收到消息: " + message);
//        ack.acknowledge(); //手动确认
//    }
//}
