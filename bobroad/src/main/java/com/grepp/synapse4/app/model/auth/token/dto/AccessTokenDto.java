package com.grepp.synapse4.app.model.auth.token.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class AccessTokenDto {
    
    private String id;
    private String token;
    private Long expiresIn;

}
