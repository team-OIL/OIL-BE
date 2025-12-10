package com.example.OIL.global.exeption;

import com.example.OIL.global.error.exception.ErrorCode;
import com.example.OIL.global.error.exception.OILException;

public class ExpiredJwt extends OILException {

    public static final OILException EXCEPTION = new ExpiredJwt();


    private ExpiredJwt() {
        super(ErrorCode.EXPIRED_JWT);
    }
}
