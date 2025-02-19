package TripleS.VidiLang.folder.entity;

import TripleS.VidiLang.global.exception.ErrorCode;
import TripleS.VidiLang.global.exception.model.CustomException;

public enum ColorType {

    RED("RED"),
    PINK("PINK"),
    GREEN("GREEN"),
    YELLOW("YELLOW"),
    PURPLE("PURPLE"),
    BLUE("BLUE"),
    SKYBLUE("SKYBLUE"),
    GRAY("GRAY");

    private final String displayCode;

    ColorType(String displayCode) {
        this.displayCode = displayCode;
    }

    public static ColorType getColorTypeOfString(String roleType) {
        for (ColorType type : ColorType.values()) {
            if (type.displayCode.equals(roleType)) {
                return type;
            }
        }

        throw new CustomException(ErrorCode.INVALID_ROLE_TYPE_EXCEPTION, ErrorCode.INVALID_TOKEN_EXCEPTION.getMessage());
    }
}
