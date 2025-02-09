package TripleS.VidiLang.global.exception.model;

import TripleS.VidiLang.global.exception.ErrorCode;

public class TokenValidFailedException extends CustomException {
    public TokenValidFailedException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
