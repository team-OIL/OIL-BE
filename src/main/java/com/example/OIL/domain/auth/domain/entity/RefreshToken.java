package com.example.OIL.domain.auth.domain.entity;

//import jakarta.persistence.Id;
import org.springframework.data.annotation.Id;
import lombok.*;
import org.springframework.data.redis.core.RedisHash;
import org.springframework.data.redis.core.TimeToLive;
import org.springframework.data.redis.core.index.Indexed;

@Getter
@Builder
@RedisHash("refresh_token")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public class RefreshToken {

    @Id
    private Long userId;

    @Indexed
    private String refreshToken;

    @TimeToLive
    private Long ttl;

    public void update(String token, Long ttl) {
        this.refreshToken = token;
        this.ttl = ttl;
    }
}