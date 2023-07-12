package com.ebidding.gpt.api;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;

import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import io.netty.handler.proxy.Socks5ProxyHandler;
import io.netty.channel.ChannelOption;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Service
public class GPTClient {

    private final WebClient webClient;

    public GPTClient(@Value("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") String apiKey) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .proxy(proxy -> proxy.type(ProxyProvider.Proxy.SOCKS5).host("localhost").port(10808));

        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Flux<ServerSentEvent> streamResponses(ChatRequestDTO chatRequest) {
        return this.webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToFlux(ServerSentEvent.class);
    }
}

