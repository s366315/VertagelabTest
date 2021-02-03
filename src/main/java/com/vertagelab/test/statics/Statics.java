package com.vertagelab.test.statics;

import com.vertagelab.test.model.ErrorResponseModel;
import com.vertagelab.test.model.SuccessResponseModel;

public class Statics {
    public static SuccessResponseModel getSuccessResponse() {
        SuccessResponseModel response = new SuccessResponseModel();
        response.setSuccess(true);
        return response;
    }

    public static ErrorResponseModel getErrorResponse(String message) {
        ErrorResponseModel response = new ErrorResponseModel();
        response.setError(message);
        response.setSuccess(false);
        return response;
    }
}
