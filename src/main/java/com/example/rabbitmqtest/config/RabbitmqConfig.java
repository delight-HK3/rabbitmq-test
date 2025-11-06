package com.example.rabbitmqtest.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lombok.RequiredArgsConstructor;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
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

    @Value("${spring.rabbitmq.host}")
    private String host;     // 접속 호스트

    @Value("${spring.rabbitmq.port}")
    private Integer port;     // 접속 포트번호

    @Value("${spring.rabbitmq.username}")
    private String username; // 접속 아이디

    @Value("${spring.rabbitmq.password}")
    private String password; // 접속 비밀번호

    private static final String BINDING_KEY = "test.key";
    private static final String EXCHANGE_NAME = "test.exchange";
    private static final String QUEUE_NAME = "queue";

    /**
     * direct Exchange 구성
     *
     * @return DirectExchange
     */
    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(EXCHANGE_NAME);
    }

    /**
     * Queue 구성
     *
     * @return Queue
     */
    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME);
    }

    /**
     * Queue와 DirectExchange를 바인딩
     * test.key라는 이름으로 바인딩 구성
     *
     * @param directExchange
     * @param queue
     * @return Binding
     */
    @Bean
    public Binding binding(DirectExchange directExchange, Queue queue) {
        // queue까지 가는 바인딩 Exchange 타입을 directExchange로 지정하고 test.key 이름으로 바인딩 구성
        return BindingBuilder
                .bind(queue)
                .to(directExchange)
                .with(BINDING_KEY);
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
