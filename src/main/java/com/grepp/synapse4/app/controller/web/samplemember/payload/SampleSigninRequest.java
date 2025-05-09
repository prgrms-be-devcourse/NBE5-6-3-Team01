package com.grepp.synapse4.app.controller.web.samplemember.payload;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class SampleSigninRequest {
    
    @NotBlank
    private String userId;
    
    @NotBlank
    @Size(min = 4, max = 10)
    private String password;
    
}
