//package com.dfg.java_template.framework.mq.rabbitmq.demo;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.web.bind.annotation.PostMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RestController;
//
//import javax.annotation.Resource;
//
//@RestController
//@RequestMapping("/rabbit/demo")
//@Slf4j
//public class RabbitDemoController {
//    @Resource
//    private DemoProducer demoProducer;
//
//    @PostMapping("/sendDemoMessage")
//    public void sendDemoMessage() {
//        demoProducer.sendDemoMessage();
//    }
//
//    @PostMapping("/sendBatchDemoMessage")
//    public void sendBatchDemoMessage() {
//        demoProducer.sendBatchDemoMessage();
//    }
//
//
//}
