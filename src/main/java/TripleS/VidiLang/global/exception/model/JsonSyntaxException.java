package TripleS.VidiLang.global.exception.model;

import TripleS.VidiLang.global.exception.ErrorCode;

public class JsonSyntaxException extends CustomException{
    public JsonSyntaxException(ErrorCode errorCode, String message) {
        super(errorCode, message);
    }
}
