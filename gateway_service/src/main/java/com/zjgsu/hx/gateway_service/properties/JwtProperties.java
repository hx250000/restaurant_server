package com.zjgsu.hx.gateway_service.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
@ConfigurationProperties(prefix = "security.jwt")
@Data
public class JwtProperties {

    /**
     * 白名单路径
     */
    private List<String> whitelist = new ArrayList<>();
}
