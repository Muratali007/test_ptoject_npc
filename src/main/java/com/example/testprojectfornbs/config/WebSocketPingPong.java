package com.example.testprojectfornbs.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Component
public class WebSocketPingPong {

  @Autowired
  private SimpMessagingTemplate messagingTemplate;

  private int pingCount = 0;

  @Scheduled(fixedRate = 5000)
  public void sendPing() {
    messagingTemplate.convertAndSend("/topic/ping", "Ping #" + pingCount);
    pingCount++;
  }

  @EventListener
  public void handleDisconnectEvent(SessionDisconnectEvent event) {
    System.out.println("Client disconnected. Resetting ping count.");
    pingCount = 0;
  }
}
