//package com.dfg.java_template.framework.mq.rabbitmq.demo;
//
//import com.rabbitmq.client.Channel;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.amqp.core.Message;
//import org.springframework.amqp.rabbit.annotation.RabbitListener;
//import org.springframework.stereotype.Service;
//
//import java.io.IOException;
//
//@Service
//@Slf4j
//public class DemoConsumer {
//
////    @RabbitListener(queues = DemoConfig.DEMO_QUEUE, ackMode = "MANUAL")  // 方法级别的ackMode会覆盖配置级别的ackMode
////    public void handleDemoMessage(QueueDemo queueDemo, Message message, Channel channel) {
////        log.info("接收到消息: {}", queueDemo);
////        long deliveryTag = message.getMessageProperties().getDeliveryTag();
////        try {
////            // 手动确认消息已被成功处理
////            // deliveryTag表示每个消息在 Channel 上的唯一递增编号
////            // multiple表示是否批量确认;
////            // false表示只确认当前这条消息,true表示确认 deliveryTag 及之前的所有消息
////            channel.basicAck(deliveryTag, false);
////            log.info("订单处理成功并确认: {}", queueDemo.getId());
////
////        } catch (Exception e) {
////            log.error("订单处理失败: {}", queueDemo.getId(), e);
////
////            try {
////                // deliveryTag,multiple,requeue
////                // requeue: true表示重新入队，false表示丢弃或进入死信队列
////                // 这里选择重新入队，最多重试3次（建议配合重试配置）
////                channel.basicNack(deliveryTag, false, true);
////                log.warn("消息重新入队: {}", queueDemo.getId());
////            } catch (IOException ex) {
////                log.error("消息确认失败", ex);
////            }
////        }
////    }
//
//    @RabbitListener(queues = DemoConfig.DEMO_DLX_QUEUE, ackMode = "MANUAL")
//    public void handleExpiredOrder(QueueDemo queueDemo, Message message, Channel channel) {
//        log.info("接收到消息: {}", queueDemo);
//        long deliveryTag = message.getMessageProperties().getDeliveryTag();
//        try {
//            if (queueDemo.getStatus() == 0) {
//                channel.basicAck(deliveryTag, false);
//                log.info("过期订单处理成功并确认: {}", queueDemo.getId());
//            }
//        } catch (Exception e) {
//            log.error("过期订单处理失败: {}", queueDemo.getId(), e);
//        }
//    }
//}
