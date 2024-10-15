package com.ajmayen.commonmodule.util;

import com.ajmayen.commonmodule.model.CustomHttpResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Map;

public class ResponseBuilder {

    public static ResponseEntity<CustomHttpResponse> buildSuccessResponse(HttpStatus httpStatus, Map<String,
                                                                        Object> responseBody){
      //  return new ResponseEntity<>(responseBody,httpStatus);
        CustomHttpResponse successResponse = CustomHttpResponse
                .builder()
                .httpStatus(httpStatus)
                .responsebody(responseBody)
                .build();
        return new ResponseEntity<>(successResponse, httpStatus);
    }

    public static ResponseEntity<CustomHttpResponse> buildFailureResponse(HttpStatus httpStatus,
                                                               String errorCode,String errorMessage){

        CustomHttpResponse errorResponse = CustomHttpResponse
                .builder()
                .httpStatus(httpStatus)
                .errorbody(Map.of("error_Code",errorCode,"error_message",errorMessage))
                .build();
                 return new ResponseEntity<>(errorResponse, httpStatus);

    }
}
