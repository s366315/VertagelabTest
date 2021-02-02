package com.vertagelab.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ErrorResponseModel extends BaseResponseModel {
    private Object error;
}
