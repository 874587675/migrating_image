//package com.dfg.java_template.framework.mq.rabbitmq.demo;
//
//import org.springframework.amqp.core.*;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.util.HashMap;
//import java.util.Map;
//
//@Configuration
//public class DemoConfig {
//
//    // 队列名称
//    public static final String DEMO_QUEUE = "demo.queue";
//    // 交换机名称
//    public static final String DEMO_EXCHANGE = "demo.exchange";
//    // 路由键
//    public static final String DEMO_ROUTING_KEY = "demo.routing.key";
//
//
//    // 死信队列名称
//    public static final String DEMO_DLX_QUEUE = "demo.dlx.queue";
//    // 死信交换机名称
//    public static final String DEMO_DLX_EXCHANGE = "demo.dlx.exchange";
//    // 死信路由键
//    public static final String DEMO_DLX_ROUTING_KEY = "demo.expired";
//
//    /**
//     * 创建队列
//     */
//    @Bean
//    public Queue queueDemo() {
//        Map<String, Object> args = new HashMap<>();
//        // 设置死信交换机
//        args.put("x-dead-letter-exchange", DEMO_DLX_EXCHANGE);
//        // 设置死信路由键
//        args.put("x-dead-letter-routing-key", DEMO_DLX_ROUTING_KEY);
//        // 可选：设置队列中消息的 TTL（毫秒），例如 15 秒后未消费进入死信 队列TTL优先级低于消息TTL
//        args.put("x-message-ttl", 15000);
//
//        // durable: 持久化, exclusive: 非独占, autoDelete: 不自动删除
//        return new Queue(DEMO_QUEUE, true, false, false, args);
//    }
//
//
//    /**
//     * 创建交换机
//     */
//    @Bean
//    public DirectExchange demoExchange() {
//        // durable: 持久化, autoDelete: 不自动删除
//        return new DirectExchange(DEMO_EXCHANGE, true, false);
//    }
//
//    /**
//     * 绑定队列到交换机
//     */
//    @Bean
//    public Binding demoBinding() {
//        return BindingBuilder
//                .bind(queueDemo())
//                .to(demoExchange())
//                .with(DEMO_ROUTING_KEY);
//    }
//
//    /**
//     * 创建死信队列
//     */
//    @Bean
//    public Queue queueDemoDlx() {
//        return QueueBuilder.durable(DEMO_DLX_QUEUE).build();
//    }
//
//    /**
//     * 创建死信交换机
//     */
//    @Bean
//    public DirectExchange demoDlxExchange() {
//        return new DirectExchange(DEMO_DLX_EXCHANGE);
//    }
//
//    /**
//     * 绑定死信队列到死信交换机
//     */
//    @Bean
//    public Binding orderDlxBinding() {
//        return BindingBuilder.bind(queueDemoDlx())
//                .to(demoDlxExchange())
//                .with(DEMO_DLX_ROUTING_KEY);
//    }
//}
