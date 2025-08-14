package com.chatapp.configuration.jwt;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Component
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ConfigurationProperties(prefix = "app.jwt")
public class JwtProperties {
    private String secret;
    private String issuer;
    private int accessTokenTtlMinutes;
    private int refreshTokenTtlDays;

}
