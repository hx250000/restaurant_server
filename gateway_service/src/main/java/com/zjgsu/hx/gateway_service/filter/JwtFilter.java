package com.zjgsu.hx.gateway_service.filter;

import com.zjgsu.hx.gateway_service.properties.JwtProperties;
import com.zjgsu.hx.gateway_service.util.JwtUtil;
import io.jsonwebtoken.Claims;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Arrays;
import java.util.List;

@Component
public class JwtFilter implements GlobalFilter, Ordered {
    private static final Logger log = LoggerFactory.getLogger(JwtFilter.class);

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private JwtProperties jwtProperties;

    private boolean isWhiteListed(String path) {
        return jwtProperties.getWhitelist().stream().anyMatch(path::startsWith);
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {

        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        HttpMethod method = request.getMethod();

        log.info("[JwtFilter]拦截到请求{} {}",method,path);

        // 0️⃣ OPTIONS 预检请求直接放行（CORS 必须）
        if (HttpMethod.OPTIONS.equals(method)) {
            log.debug("OPTIONS 预检请求放行: {}", path);
            return chain.filter(exchange);
        }

        // 1️⃣ 白名单路径放行
        if (isWhiteListed(path)) {
            log.info("白名单路径放行: {}", path);
            return chain.filter(exchange);
        }

        // 2️⃣ 获取 Authorization 头
        String authHeader = request.getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        log.info("[JwtFilter]请求头:{}",authHeader);
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            log.warn("请求未携带有效 Authorization 头: {}", path);
            return unauthorized(exchange);
        }

        // 3️⃣ 解析 Token
        String token = authHeader.substring(7);
        try {
            Claims claims = jwtUtil.parseToken(token);

            // （可选）把 userId 传给下游服务
            String userId = String.valueOf(claims.get("userId"));

            ServerHttpRequest newRequest = request.mutate()
                    .header("X-User-Id", userId)
                    .build();

            return chain.filter(exchange.mutate().request(newRequest).build());

        } catch (Exception e) {
            log.warn("Token 校验失败: {}, path={}", e.getMessage(), path);
            return unauthorized(exchange);
        }
    }

    /**
     * 返回 401 未授权
     */
    private Mono<Void> unauthorized(ServerWebExchange exchange) {
        ServerHttpResponse response = exchange.getResponse();
        response.setStatusCode(HttpStatus.UNAUTHORIZED);
        return response.setComplete();
    }

    @Override
    public int getOrder() {
        return -100; // 优先级高，先执行认证
    }

}
