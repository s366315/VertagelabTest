package com.vertagelab.test.exception;

import com.vertagelab.test.model.ErrorResponseModel;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class ExceptionInterceptor {

    @ExceptionHandler(RequestException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ResponseBody
    public ErrorResponseModel handleNoRecordFoundException(RequestException ex)
    {
        ErrorResponseModel errorResponse = new ErrorResponseModel();
        errorResponse.setError("No Record Found");
        return errorResponse;
    }
}
