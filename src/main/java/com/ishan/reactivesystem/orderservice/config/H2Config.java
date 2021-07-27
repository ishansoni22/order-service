package com.ishan.reactivesystem.orderservice.config;

import org.h2.tools.Server;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class H2Config {

  private Server webServer;

  private Server tcpServer;

  @EventListener(org.springframework.context.event.ContextRefreshedEvent.class)
  public void start() throws java.sql.SQLException {
    this.webServer = org.h2.tools.Server.createWebServer("-webPort", "4032", "-tcpAllowOthers")
        .start();
    this.tcpServer = org.h2.tools.Server.createTcpServer("-tcpPort", "4042", "-tcpAllowOthers")
        .start();
  }

  @EventListener(org.springframework.context.event.ContextClosedEvent.class)
  public void stop() {
    this.tcpServer.stop();
    this.webServer.stop();
  }

}
