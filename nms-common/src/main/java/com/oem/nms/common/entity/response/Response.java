package com.oem.nms.common.entity.response;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.Setter;

/**
 * @author Seaway John
 */
@Getter
@Setter
public class Response<T> {

    private boolean error;

    @JsonIgnore
    private ResponseCode responseCode;

    private String code;

    private String message;

    private T data;

    private Response() {
    }

    public Response(T data) {
        this.error = false;
        this.responseCode = ResponseCode.OK;
        this.code = this.responseCode.getCode();
        this.message = this.responseCode.getMessage();
        this.data = data;
    }

    public Response(ResponseCode responseCode) {
        this.error = true;
        this.responseCode = responseCode;
        this.code = this.responseCode.getCode();
        this.message = this.responseCode.getMessage();
    }

    public Response(ResponseCode responseCode, String message) {
        this.error = true;
        this.responseCode = responseCode;
        this.code = this.responseCode.getCode();
        this.message = message;
    }

}
