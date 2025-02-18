package TripleS.VidiLang.folder.entity;

import TripleS.VidiLang.global.exception.ErrorCode;
import TripleS.VidiLang.global.exception.model.CustomException;

public enum LanguageType {

    ENGLISH("English"),
    SPANISH("Spanish"),
    CHINESE("Chinese"),
    JAPANESE("Japanese"),
    PERSIAN("Persian"),
    RUSSIAN("Russian"),
    DEUTSCH("Deutsch"),
    FRENCH("French"),;

    private final String displayCode;

    LanguageType(String displayCode) {
        this.displayCode = displayCode;
    }

    public static LanguageType getLanguageTypeOfString(String roleType) {
        for (LanguageType type : LanguageType.values()) {
            if (type.displayCode.equals(roleType)) {
                return type;
            }
        }

        throw new CustomException(ErrorCode.INVALID_ROLE_TYPE_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
    }
}
