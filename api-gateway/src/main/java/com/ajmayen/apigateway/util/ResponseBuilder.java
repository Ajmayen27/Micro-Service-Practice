package com.ajmayen.apigateway.util;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    public static ResponseEntity<?> buildSuccessResponse(HttpStatus httpStatus, Map<String,
                                                                        Object> responseBody){
        return new ResponseEntity<>(responseBody,httpStatus);
    }

    public static ResponseEntity<?> buildFailureResponse(HttpStatus httpStatus,
                                                               Integer errorCode,String errorMessage){
        Map<String,Object> errorResponse = new HashMap<>();
        errorResponse.put("errorCode",errorCode);
        errorResponse.put("errorMessage",errorMessage);
        return new ResponseEntity<>(errorResponse,httpStatus);
    }
}
