package TripleS.VidiLang.global.exception.model;

import TripleS.VidiLang.global.exception.ErrorCode;

public class BadRequestException extends CustomException{
    public BadRequestException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
