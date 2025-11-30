package com.example.rabbitmqtest.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * RabbitMQ에서 사용하는 exchange이름과 Queue정보 모아둔 Component
 */
@Component
public class RabbitmqExchangeInfo {

    @Value("${rabbitmq.direct.exchange.name}")
    private String DIRECT_EXCHANGE_NAME; // direct Exchange

    @Value("${rabbitmq.fanout.exchange.name}")
    private String FANOUT_EXCHANGE_NAME; // fanout Exchange

    @Value("${rabbitmq.header.exchange.name}")
    private String HEAD_EXCHANGE_NAME; // header Exchange

    @Value("${rabbitmq.topic.exhange.name}")
    private String TOPIC_EXHANGE_NAME; // topic exchange

    @Value("${rabbitmq.direct.exchange.key}")
    private String DIRECT_EXCHANGE_KEY; // direct Exchange Key

    @Value("${rabbitmq.direct.queue.name}")
    private String DIRECT_QUEUE_NAME;   // direct Queue name

    @Value("${rabbitmq.fanout.queue.name.one}")
    private String FANOUT_QUEUE_NAME_ONE;   // fanout queue one

    @Value("${rabbitmq.fanout.queue.name.two}")
    private String FANOUT_QUEUE_NAME_TWO;   // fanout queue two

    @Value("${rabbitmq.header.queue.name}")
    private String HEADER_QUEUE_NAME;         // header queue name

    @Value("${rabbitmq.topic.queue.name}")
    private String TOPIC_QUEUE_NAME;        // topic queue name


    public String get_DIRECT_EXCHANGE_NAME() {
        return DIRECT_EXCHANGE_NAME;
    }

    public String get_FANOUT_EXCHANGE_NAME() {
        return FANOUT_EXCHANGE_NAME;
    }

    public String get_HEADER_EXCHANGE_NAME() {
        return HEAD_EXCHANGE_NAME;
    }

    public String get_TOPIC_EXCHANGE_NAME() {
        return TOPIC_EXHANGE_NAME;
    }

    public String get_DIRECT_EXCHANGE_KEY() {
        return DIRECT_EXCHANGE_KEY;
    }

    public String get_DIRECT_QUEUE_NAME() {
        return DIRECT_QUEUE_NAME;
    }

    public String get_FANOUT_QUEUE_NAME_ONE() {
        return FANOUT_QUEUE_NAME_ONE;
    }

    public String get_FANOUT_QUEUE_NAME_TWO() {
        return FANOUT_QUEUE_NAME_TWO;
    }

    public String get_HEADER_QUEUE_NAME() {
        return HEADER_QUEUE_NAME;
    }

    public String get_TOPIC_QUEUE_NAME() {
        return TOPIC_QUEUE_NAME;
    }
}
