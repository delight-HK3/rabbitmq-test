package com.example.rabbitmqtest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 설정 파일
 */
@Configuration
@RequiredArgsConstructor
public class RabbitmqConfig {

    private final RabbitmqExchangeInfo rabbitmqExchangeInfo;

    /**
     * 메세지 성공을 못하는 경우 direct.queue로 라우팅하고 dead.queue로 이동
     */
    @Bean
    public DirectExchange deadExchange(){
        return ExchangeBuilder
                .directExchange(rabbitmqExchangeInfo.get_DEAD_EXCHANGE_NAME())
                .build();
    }

    /**
     * Direct Exchange 구성 : direct.queue를 라우팅 하는데 사용
     */
    @Bean
    public DirectExchange directExchange(){
        return ExchangeBuilder
                .directExchange(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_NAME())
                .build();
    }

    /**
     * Direct Exchange 구성 : direct.queue를 라우팅 하는데 사용
     */
    @Bean
    public DirectExchange directTTLExchange(){
        return ExchangeBuilder
                .directExchange(rabbitmqExchangeInfo.get_DIRECT_TTL_EXCHANGE_NAME())
                .build();
    }

    /**
     * deadQueue와 라우팅 키(Routing key)를 기반으로 바인딩 수행.
     *
     * @param deadQueue    성공적으로 처리하지 못한 메시지를 담는 공간
     * @param deadExchange 성공적으로 처리하지 못한 메시지를 라우팅
     */
    @Bean
    public Binding deadBinding(Queue deadQueue, DirectExchange deadExchange){
        return BindingBuilder
                .bind(deadQueue)
                .to(deadExchange)
                .with(rabbitmqExchangeInfo.get_DEAD_ROUTING_KEY());
    }

    /**
     * Direct Exchange 와 direct Queue 간의 바인딩을 수행합니다.
     *
     * @param directQueue    메시지를 담을 큐
     * @param directExchange 메시지를 담기 위한 라우팅
     */
    @Bean
    public Binding directBinding(Queue directQueue, DirectExchange directExchange){
        return BindingBuilder
                .bind(directQueue)
                .to(directExchange)
                .with(rabbitmqExchangeInfo.get_DIRECT_ROUTING_KEY());
    }

    /**
     * Direct Exchange (ttl) 와 direct Queue (ttl) 간의 바인딩을 수행합니다.
     *
     * @param directTTLQueue    메시지를 담을 큐
     * @param directTTLExchange 메시지를 담기 위한 라우팅
     */
    @Bean
    public Binding directTTLBinding(Queue directTTLQueue, DirectExchange directTTLExchange){
        return BindingBuilder
                .bind(directTTLQueue)
                .to(directTTLExchange)
                .with(rabbitmqExchangeInfo.get_DIRECT_TTL_ROUTING_KEY());
    }

    /**
     * 만약 메세지 전송에 실패하면 해당 Queue(dead.queue) 로 이동
     *
     * @return dead Queue
     */
    @Bean
    public Queue deadQueue(){
        return new Queue(rabbitmqExchangeInfo.get_DEAD_QUEUE_NAME(), true);
    }

    /**
     * directQueue 이름의 큐를 구성
     * - 해당 큐에서는 속성 값으로 x-dead-letter-exchange가 발생시 rabbit.dead 로 라우팅
     * - 해당 큐에서는 속성 값으로 x-dead-letter-routing-key를 통해 Direct Queue의 라우팅 키를 전달하여 라우팅
     *
     * @return direct Queue
     */
    @Bean
    public Queue directQueue(){
        return QueueBuilder.durable(rabbitmqExchangeInfo.get_DIRECT_QUEUE_NAME())
                .withArgument("x-dead-letter-exchange", rabbitmqExchangeInfo.get_DEAD_EXCHANGE_NAME())
                .withArgument("x-dead-letter-routing-key", rabbitmqExchangeInfo.get_DEAD_ROUTING_KEY())
                .build();
    }

    /**
     * ttl 설정된 directQueue 이름의 큐를 구성
     */
    @Bean
    public Queue directTTLQueue() {
        // TTL 설정이 미포함된 Queue
        //return new Queue(rabbitmqExchangeInfo.get_DIRECT_QUEUE_NAME(), true);

        // TTL 설정이 포함된 Queue
        // QueueBuilder에 durable 메서드 안에 Queue이름을 넣으면
        // RabbitMQ가 재부팅되도 Queue 대기열에 남는다.
        return QueueBuilder.durable(rabbitmqExchangeInfo.get_DIRECT_TTL_QUEUE_NAME())
                .withArgument("x-message-ttl", 3000)
                .build();
    }

}
