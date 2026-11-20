//package com.dfg.java_template.framework.mq.kafka.demo;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RestController
//@RequestMapping("/kafka")
//public class KafkaController {
//
//    @Autowired
//    private KafkaProducer producer;
//
//    @GetMapping("/send")
//    public String send(@RequestParam String msg) {
//        producer.sendMessage(msg);
//        return "发送成功: " + msg;
//    }
//}
