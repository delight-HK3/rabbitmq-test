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

    @Value("${rabbitmq.direct.ttl.exchange.name}")
    private String DIRECT_TTL_EXCHANGE_NAME; // direct TTL Exchange

    @Value("${rabbitmq.dead.exchange.name}")
    private String FANOUT_EXCHANGE_NAME; // dead Exchange

    @Value("${rabbitmq.direct.queue.name}")
    private String DIRECT_QUEUE_NAME;   // direct Queue name

    @Value("${rabbitmq.direct.ttl.queue.name}")
    private String DIRECT_TTL_QUEUE_NAME;   // direct TTL Queue name

    @Value("${rabbitmq.dead.queue.name}")
    private String DEAD_QUEUE_NAME;     // dead Queue name

    @Value("${rabbitmq.dead.routing.key}")
    private String DEAD_ROUTING_KEY;    // dead Routing Key

    @Value("${rabbitmq.direct.routing.key}")
    private String DIRECT_ROUTING_KEY;  // direct Routing Key

    @Value("${rabbitmq.direct.ttl.routing.key}")
    private String DIRECT_TTL_ROUTING_KEY;  // direct TTL Routing Key

    public String get_DIRECT_EXCHANGE_NAME() {
        return DIRECT_EXCHANGE_NAME;
    }

    public String get_DIRECT_TTL_EXCHANGE_NAME() { return DIRECT_TTL_EXCHANGE_NAME; }

    public String get_DEAD_EXCHANGE_NAME() {
        return FANOUT_EXCHANGE_NAME;
    }

    public String get_DIRECT_QUEUE_NAME() {return DIRECT_QUEUE_NAME; }

    public String get_DIRECT_TTL_QUEUE_NAME() { return DIRECT_TTL_QUEUE_NAME; }

    public String get_DEAD_QUEUE_NAME() { return DEAD_QUEUE_NAME; }

    public String get_DEAD_ROUTING_KEY() { return DEAD_ROUTING_KEY; }

    public String get_DIRECT_ROUTING_KEY() { return DIRECT_ROUTING_KEY; }

    public String get_DIRECT_TTL_ROUTING_KEY() { return DIRECT_TTL_ROUTING_KEY; }
}