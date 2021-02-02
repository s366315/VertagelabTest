package com.vertagelab.test.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SuccessResponseModel extends BaseResponseModel {
    private Object result;
}
