package com.nibm.smartmedicine.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse<T> {

    private Integer code;

    private LocalDateTime datetime;

    private boolean isSuccess;

    private String message;

    private T data;

    public ApiResponse(boolean b, String s) {
        this.setSuccess(b);
        this.setMessage(s);
    }
}
