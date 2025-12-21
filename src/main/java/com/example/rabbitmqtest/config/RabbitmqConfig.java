package com.example.rabbitmqtest.config;

import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 설정 파일
 */
@Configuration
@RequiredArgsConstructor
public class RabbitmqConfig {

    private final RabbitmqExchangeInfo rabbitmqExchangeInfo;


    // 공통적으로 RabbitMQ가 재부팅되도 Exchange가 삭제안되고 동시에 대기열에 남도록 설정
    @Bean
    public DirectExchange directExchange() {
        // DIRECT_EXCHANGE_NAME 이름의 direct Exchange 구성
        return ExchangeBuilder
                .directExchange(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_NAME())
                .build();
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        // FANOUT_EXCHANGE_NAME 이름의 fanout Exchange 구성
        return ExchangeBuilder
                .fanoutExchange(rabbitmqExchangeInfo.get_FANOUT_EXCHANGE_NAME())
                .build();
    }

    @Bean
    public TopicExchange topicExchange() {
        // TOPIC_EXCHANGE_NAME 이름의 topic Exchange 구성
        return ExchangeBuilder
                .topicExchange(rabbitmqExchangeInfo.get_TOPIC_EXCHANGE_NAME())
                .build();
    }

    @Bean
    public HeadersExchange headersExchange() {
        // HEADER_EXCHANGE_NAME 이름의 header Exchange 구성
        return ExchangeBuilder
                .headersExchange(rabbitmqExchangeInfo.get_HEADER_EXCHANGE_NAME())
                .build();
    }

    // 공통적으로 RabbitMQ가 재부팅되도 Queue 대기열에 남도록 설정
    @Bean
    public Queue directQueue() {
        return new Queue(rabbitmqExchangeInfo.get_DIRECT_QUEUE_NAME(), true);
    }

    @Bean
    public Queue fanoutQueueOne() {
        return new Queue(rabbitmqExchangeInfo.get_FANOUT_QUEUE_NAME_ONE(), true);
    }

    @Bean
    public Queue fanoutQueueTwo() {
        return new Queue(rabbitmqExchangeInfo.get_FANOUT_QUEUE_NAME_TWO(), true);
    }

    @Bean
    public Queue headersQueue() {
        return new Queue(rabbitmqExchangeInfo.get_HEADER_QUEUE_NAME(), true);
    }

    @Bean
    public Queue topicQueue() {
        return new Queue(rabbitmqExchangeInfo.get_TOPIC_QUEUE_NAME(), true);
    }

    /**
     * Queue와 DirectExchange를 바인딩
     */
    @Bean
    public Binding directBinding(DirectExchange directExchange, @Qualifier("directQueue") Queue directQueue) {
        // queue까지 가는 바인딩 Exchange 타입을 directExchange로 지정하고 test.key 이름으로 바인딩 구성
        return BindingBuilder
                .bind(directQueue)
                .to(directExchange)
                .with(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_KEY());
    }

    /**
     * Queue(fanoutQueueOne)와 FanoutExchange 바인딩
     * Fanout 방식은 Exchange와 연결된 모든 Queue에 보내는 방식으로
     * FanoutExchange와 연결된 fanoutQueueOne, fanoutQueueTwo에게 메세지를 보낸다.
     */
    @Bean
    public Binding fanoutBindingOne(FanoutExchange fanoutExchange, Queue fanoutQueueOne) {
        return BindingBuilder
                .bind(fanoutQueueOne)
                .to(fanoutExchange);
    }

    /**
     * Queue(fanoutQueueTwo)와 FanoutExchange 바인딩
     * Fanout 방식은 Exchange와 연결된 모든 Queue에 보내는 방식으로
     * FanoutExchange와 연결된 fanoutQueueOne, fanoutQueueTwo에게 메세지를 보낸다.
     */
    @Bean
    public Binding fanoutBindingTwo(FanoutExchange fanoutExchange, Queue fanoutQueueTwo) {
        return BindingBuilder
                .bind(fanoutQueueTwo)
                .to(fanoutExchange);
    }

    /**
     * headers Exchange 와 headersQueue간 바인딩
     * headersExchange 방식으로 headersQueue와 Header값을 조건으로 바인딩 수행
     */
    @Bean
    public Binding headerBinding(HeadersExchange headersExchange, Queue headersQueue){
        return BindingBuilder
                .bind(headersQueue)
                .to(headersExchange)
                .where("execute-key")
                .exists();
    }

    /**
     * topic Exchange 와 topicQueue간 바인딩
     * producer에서 topic.send. 으로 시작하는 라우팅 키를 보내주면 라우팅 키 규칙과 같은 Exchange와 연결
     */
    @Bean
    public Binding topicBinding(TopicExchange topicExchange, Queue topicQueue){
        return BindingBuilder
                .bind(topicQueue)
                .to(topicExchange)
                .with("topic.send.*");
    }

}
