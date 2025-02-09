package TripleS.VidiLang.global.exception.model;

import TripleS.VidiLang.global.exception.ErrorCode;

public class UnauthorizedException extends CustomException {
    public UnauthorizedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
