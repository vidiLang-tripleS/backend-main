package TripleS.VidiLang.global.exception.model;

import TripleS.VidiLang.global.exception.ErrorCode;

public class NotFoundException extends CustomException {
    public NotFoundException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
