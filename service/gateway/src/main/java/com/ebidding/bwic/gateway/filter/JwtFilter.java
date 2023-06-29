package com.ebidding.bwic.gateway.filter;


import com.auth0.jwt.interfaces.DecodedJWT;
import com.ebidding.common.utils.JwtUtils;
import org.springframework.cloud.gateway.filter.GatewayFilter;
import org.springframework.cloud.gateway.filter.factory.AbstractGatewayFilterFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;


import java.util.ArrayList;
import java.util.List;

@Component
public class JwtFilter extends AbstractGatewayFilterFactory {
    @Override
    public GatewayFilter apply(Object config) {
        // Authorization: Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJpZCI6IjEiLCJuYW1lIjoiS3VvIiwicm9sZSI6IlRSQURFUiJ9.9AisoVbr5HLULQy6WQpfBrpCGTIYE7L58NLTAv2wEZk
        return (exchange, chain) -> {
            List<String> headers = exchange.getRequest().getHeaders().getOrDefault(HttpHeaders.AUTHORIZATION, new ArrayList<>());
            System.out.println("headers: " + headers);
            if (headers.size() > 0) {
                String authorization = headers.get(0);
                String[] splits = authorization.split("\\s");
                if (splits.length == 2) {
                    String token = splits[1];
                    try {
                        DecodedJWT decodedJWT = JwtUtils.VerifyToken(token);
                        //If you want to build a "pre" filter you need to manipulate the
                        //request before calling chain.filter
                        ServerHttpRequest.Builder builder = exchange.getRequest().mutate();
                        //use builder to manipulate the request
                        return chain.filter(exchange.mutate().request(builder.build()).build());
                    } catch (Exception ex) {
                        return OnUnAuthorized(exchange);
                    }
                }
            }
            return OnUnAuthorized(exchange);
        };
    }

    private Mono<Void> OnUnAuthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

}
