package com.ebidding.bid.service;

import com.ebidding.bid.domain.chat.ChatRequestDTO;
import io.netty.channel.ChannelOption;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.http.codec.ServerSentEvent;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.transport.ProxyProvider;

@Service
public class GPTService {

    private final WebClient webClient;

    public GPTService(@Value("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") String apiKey) {
        HttpClient httpClient = HttpClient.create()
                .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, 5000)
                .proxy(proxy -> proxy.type(ProxyProvider.Proxy.SOCKS5).host("localhost").port(10808));

        this.webClient = WebClient.builder()
                .baseUrl("https://api.openai.com/v1/chat/completions")
                .clientConnector(new ReactorClientHttpConnector(httpClient))
                .defaultHeader("Authorization", "Bearer " + apiKey)
                .build();
    }

    public Flux<ServerSentEvent> chatWithGPT(ChatRequestDTO chatRequest) {
        return this.webClient.post()
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(chatRequest)
                .retrieve()
                .bodyToFlux(ServerSentEvent.class)
                .takeWhile(event -> {
                    // 检查 finish_reason 是否为 "stop"，如果是，则停止解析
                    String eventData = event.data().toString();
                    return !eventData.contains("\"finish_reason\":\"stop\"");
                });
    }
}

