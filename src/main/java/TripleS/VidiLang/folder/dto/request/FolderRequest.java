package TripleS.VidiLang.folder.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FolderRequest(
        @Schema(description = "폴더명", example = "나의 불어 노트") String name,
        @Schema(description = "색상 타입 - Screaming_snake_case를 준수", example = "RED") String colorType,
        @Schema(description = "언어 타입 - Screaming_snake_case를 준수", example = "FRENCH") String languageType
) {
}
