package com.dyndyn.mvcapp.configuration;

import com.dyndyn.mvcapp.interceptor.HttpSessionHandshakeInterceptor;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;

/**
 * Configuration class for using WebSocket.
 *
 * @author Roman Dyndyn
 */
@Configuration
@EnableWebSocketMessageBroker
public class WebSocketStompConfig extends AbstractWebSocketMessageBrokerConfigurer {

    /**
     * Register /tracker as a STOMP endpoint.
     */
    @Override
    public void registerStompEndpoints(final StompEndpointRegistry registry) {
        registry.addEndpoint("/auction").withSockJS()
                .setInterceptors(new HttpSessionHandshakeInterceptor());
    }

    /**
     * Configure the message broker, set prefix for messages from server to client.
     */
    @Override
    public void configureMessageBroker(final MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/queue");
        registry.setApplicationDestinationPrefixes("/app");
    }
}
