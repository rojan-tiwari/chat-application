package com.chatapp.util;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BaseResponse<T> {
    private int statusCode;
    private T message;
    private T data;

    public static <T> BaseResponse<T> success(T data , T message) {
        return new BaseResponse<>(200, message, data);
    }

    public static <T> BaseResponse<T> error(int statusCode, T message) {
        return new BaseResponse<>(statusCode, message, null);
    }
}
