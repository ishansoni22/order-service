package com.ishan.reactivesystem.orderservice.config;

import com.rabbitmq.client.ConnectionFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Schedulers;
import reactor.rabbitmq.BindingSpecification;
import reactor.rabbitmq.ExchangeSpecification;
import reactor.rabbitmq.QueueSpecification;
import reactor.rabbitmq.RabbitFlux;
import reactor.rabbitmq.Receiver;
import reactor.rabbitmq.ReceiverOptions;
import reactor.rabbitmq.Sender;
import reactor.rabbitmq.SenderOptions;

@Configuration
@Slf4j
public class RabbitConfig {

  @Value("${rabbitmq.host:127.0.0.1}")
  private String rabbitmqHost;

  @Value("${rabbitmq.virtualHost:/}")
  private String rabbitmqVirtualHost;

  @Value("${rabbitmq.port:5672}")
  private String rabbitmqPort;

  @Value("${rabbitmq.username:test}")
  private String username;

  @Value("${rabbitmq.password:test}")
  private String password;

  @Bean
  public ConnectionFactory rabbitMQConnectionFactory() {
    ConnectionFactory connectionFactory = new ConnectionFactory();
    connectionFactory.setHost(rabbitmqHost);
    connectionFactory.setPort(Integer.parseInt(rabbitmqPort));
    connectionFactory.setUsername(username);
    connectionFactory.setPassword(password);
    connectionFactory.setVirtualHost(rabbitmqVirtualHost);
    connectionFactory.useNio();
    return connectionFactory;
  }

  @Bean
  public Sender sender(ConnectionFactory rabbitMQConnectionFactory) {
    SenderOptions senderOptions = new SenderOptions()
        .connectionFactory(rabbitMQConnectionFactory)
        .resourceManagementScheduler(Schedulers.boundedElastic());

    Sender sender = RabbitFlux.createSender(senderOptions);

    sender.declareExchange(
        ExchangeSpecification
            .exchange("order-exchange")
            .type("topic")
            .durable(true)
            .autoDelete(false)
    )
        .then(sender.declareQueue(QueueSpecification.queue("order-queue")))
        .then(sender
            .bind(BindingSpecification.binding().queue("order-queue").exchange("order-exchange")
                .routingKey("order")))
        .subscribe();

    return sender;
  }

  @Bean
  public Receiver receiver(ConnectionFactory rabbitMQConnectionFactory) {
    ReceiverOptions receiverOptions = new ReceiverOptions()
        .connectionFactory(rabbitMQConnectionFactory)
        .connectionSubscriptionScheduler(Schedulers.boundedElastic());

    return RabbitFlux.createReceiver(receiverOptions);
  }

}