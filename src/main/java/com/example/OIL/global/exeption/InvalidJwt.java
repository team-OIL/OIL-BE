package com.example.OIL.global.exeption;

import com.example.OIL.global.error.exception.ErrorCode;
import com.example.OIL.global.error.exception.OILException;


public class InvalidJwt extends OILException {

    public static final OILException EXCEPTION = new InvalidJwt();

    private InvalidJwt() {
        super(ErrorCode.INVALID_JWT);
    }
}