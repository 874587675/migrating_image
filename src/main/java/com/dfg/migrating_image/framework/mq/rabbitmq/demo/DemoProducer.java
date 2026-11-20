//package com.dfg.java_template.framework.mq.rabbitmq.demo;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.rabbit.core.RabbitTemplate;
//import org.springframework.stereotype.Service;
//
//import javax.annotation.Resource;
//import java.util.ArrayList;
//import java.util.List;
//
//@Service
//@Slf4j
//public class DemoProducer {
//    @Resource
//    private RabbitTemplate rabbitTemplate;
//
//    public void sendDemoMessage() {
//        QueueDemo queueDemo = new QueueDemo("1", "测试消息", 0);
//        rabbitTemplate.convertAndSend(DemoConfig.DEMO_EXCHANGE, DemoConfig.DEMO_ROUTING_KEY, queueDemo);
//
//        log.info("发送完成: {}", queueDemo.getId());
//    }
//
//    public void sendBatchDemoMessage() {
//        List<QueueDemo> queueDemoList = new ArrayList<>();
//        for (int i = 0; i < 10000; i++) {
//            QueueDemo queueDemo = new QueueDemo(i + "", "测试消息" + i, 0);
//            queueDemoList.add(queueDemo);
//        }
//        rabbitTemplate.invoke(operations -> {
//            for (QueueDemo queueDemo : queueDemoList) {
//                operations.convertAndSend(DemoConfig.DEMO_EXCHANGE,
//                        DemoConfig.DEMO_ROUTING_KEY,
//                        queueDemo);
//            }
//            return null;
//        });
//        log.info("原生批量发送完成，共: {} 条", queueDemoList.size());
//    }
//
//
//}
