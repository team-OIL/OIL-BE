package com.example.OIL.global.error.exception;

import lombok.Getter;

@Getter
public class OILException extends RuntimeException{

    private final ErrorProperty errorProperty;

    public OILException(ErrorProperty errorProperty) {
        super(errorProperty.getMessage());
        this.errorProperty = errorProperty;
    }
}
