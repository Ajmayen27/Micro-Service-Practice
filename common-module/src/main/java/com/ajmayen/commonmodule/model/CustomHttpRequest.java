package com.ajmayen.commonmodule.model;

import lombok.*;
import org.springframework.http.HttpMethod;

import java.util.Map;
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
public class CustomHttpRequest {

    private String requestId;
    private HttpMethod methodType;
    private String url;
    private Map<String, String> headParametarMap;
    private Map<String, String> queryParametarMap;
    private Map<String, Object> bodyParametarMap;
}
