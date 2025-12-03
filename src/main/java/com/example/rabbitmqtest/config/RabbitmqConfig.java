package com.example.rabbitmqtest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * RabbitMQ 설정 파일
 */
@EnableRabbit // RabbitMQ의 설정을 활성화 하기위해 필요
@Configuration
@RequiredArgsConstructor
public class RabbitmqConfig {

    private final RabbitmqExchangeInfo rabbitmqExchangeInfo;

    @Value("${spring.rabbitmq.host}")
    private String host; // 접속 호스트

    @Value("${spring.rabbitmq.port}")
    private Integer port; // 접속 포트번호

    @Value("${spring.rabbitmq.username}")
    private String username; // 접속 아이디

    @Value("${spring.rabbitmq.password}")
    private String password; // 접속 비밀번호


    // 공통적으로 RabbitMQ가 재부팅되도 Exchange가 삭제안되고 동시에 대기열에 남도록 설정
    @Bean
    public DirectExchange directExchange() {
        // DIRECT_EXCHANGE_NAME 이름의 direct Exchange 구성
        return new DirectExchange(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_NAME(),true, false);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        // FANOUT_EXCHANGE_NAME 이름의 fanout Exchange 구성
        return new FanoutExchange(rabbitmqExchangeInfo.get_FANOUT_EXCHANGE_NAME(),true, false);
    }

    @Bean
    public TopicExchange topicExchange() {
        // TOPIC_EXCHANGE_NAME 이름의 topic Exchange 구성
        return new TopicExchange(rabbitmqExchangeInfo.get_TOPIC_EXCHANGE_NAME(), true, false);
    }

    @Bean
    public HeadersExchange headersExchange() {
        // HEADER_EXCHANGE_NAME 이름의 header Exchange 구성
        return new HeadersExchange(rabbitmqExchangeInfo.get_HEADER_EXCHANGE_NAME(),true, false);
    }

    // 공통적으로 RabbitMQ가 재부팅되도 Queue 대기열에 남도록 설정
    @Bean public Queue directQueue() {
        return new Queue(rabbitmqExchangeInfo.get_DIRECT_QUEUE_NAME(),true);
    }

    @Bean public Queue fanoutQueueOne() {
        return new Queue(rabbitmqExchangeInfo.get_FANOUT_QUEUE_NAME_ONE(), true);
    }

    @Bean public Queue fanoutQueueTwo() {
        return new Queue(rabbitmqExchangeInfo.get_FANOUT_QUEUE_NAME_TWO(), true);
    }

    @Bean public Queue headersQueue() {
        return new Queue(rabbitmqExchangeInfo.get_HEADER_QUEUE_NAME(), false);
    }

    @Bean public Queue topicQueue() { return new Queue(rabbitmqExchangeInfo.get_TOPIC_QUEUE_NAME(), false); }

    /**
     * Queue와 DirectExchange를 바인딩
     */
    @Bean
    public Binding directBinding(DirectExchange directExchange, @Qualifier("directQueue") Queue queue) {
        // queue까지 가는 바인딩 Exchange 타입을 directExchange로 지정하고 test.key 이름으로 바인딩 구성
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(rabbitmqExchangeInfo.get_DIRECT_EXCHANGE_KEY());
    }

    /**
     * Queue(fanoutQueueOne)와 FanoutExchange 바인딩
     * Fanout 방식은 Exchange와 연결된 모든 Queue에 보내는 방식으로
     * FanoutExchange와 연결된 fanoutQueueOne, fanoutQueueTwo에게 메세지를 보낸다.
     */
    @Bean
    public Binding fanoutBindingOne(FanoutExchange fanoutExchange, @Qualifier("fanoutQueueOne") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(fanoutExchange);
    }

    /**
     * Queue(fanoutQueueTwo)와 FanoutExchange 바인딩
     * Fanout 방식은 Exchange와 연결된 모든 Queue에 보내는 방식으로
     * FanoutExchange와 연결된 fanoutQueueOne, fanoutQueueTwo에게 메세지를 보낸다.
     */
    @Bean
    public Binding fanoutBindingTwo(FanoutExchange fanoutExchange, @Qualifier("fanoutQueueTwo") Queue queue) {
        return BindingBuilder
                .bind(queue)
                .to(fanoutExchange);
    }

    /**
     * headers Exchange 와 headersQueue간 바인딩
     * headersExchange 방식으로 headersQueue와 Header값을 조건으로 바인딩 수행
     */
    @Bean
    public Binding headerBinding(HeadersExchange headersExchange, @Qualifier("headersQueue") Queue queue){
        return BindingBuilder
                .bind(queue)
                .to(headersExchange)
                .where("execute-key")
                .exists();
    }

    /**
     * topic Exchange 와 topicQueue간 바인딩
     * producer에서 topic.send. 으로 시작하는 라우팅 키를 보내주면 라우팅 키 규칙과 같은 Exchange와 연결
     */
    @Bean
    public Binding topicBinding(TopicExchange topicExchange, @Qualifier("topicQueue") Queue queue){
        return BindingBuilder
                .bind(queue)
                .to(topicExchange)
                .with("topic.send.*");
    }

    /**
     * RabbitMQ와 메시지 통신을 담당하는 클래스
     */
    @Bean
    public RabbitTemplate rabbitTemplate(){
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory());
        rabbitTemplate.setMessageConverter(messageConverter());

        return rabbitTemplate;
    }

    /**
     * RabbitMQ와 연결을 관리하는 클래스
     */
    @Bean
    public ConnectionFactory connectionFactory(){
        CachingConnectionFactory connectionFactory = new CachingConnectionFactory();
        connectionFactory.setHost(host);
        connectionFactory.setPort(port);
        connectionFactory.setUsername(username);
        connectionFactory.setPassword(password);

        return connectionFactory;
    }

    /**
     * RabbitMQ 메시지를 JSON형식으로 보내고 받을 수 있다.
     */
    @Bean
    public Jackson2JsonMessageConverter messageConverter() {

        ObjectMapper objectMapper = new ObjectMapper()
                // 날짜 관련 타임스탬프 직렬화를 막고 ISO-8601 형태로 포맷된다.
                .configure(SerializationFeature.WRITE_DATE_KEYS_AS_TIMESTAMPS, true)
                .registerModule(dateTimeModule()); // Java에서 시간을 처리하기위한 모듈

        return new Jackson2JsonMessageConverter(objectMapper);
    }

    /**
     * 자바 시간 모듈 등록
     */
    @Bean
    public JavaTimeModule dateTimeModule() {
        return new JavaTimeModule();
    }
}
