package TripleS.VidiLang.global.exception.model;

import TripleS.VidiLang.global.exception.ErrorCode;
import lombok.Getter;

// 추후 서비스 이름으로 네이밍 수정
@Getter
public class CustomException extends RuntimeException {
    private final ErrorCode errorCode;

    public CustomException(ErrorCode errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getHttpStatus() {
        return errorCode.getHttpStatusCode();
    }
}