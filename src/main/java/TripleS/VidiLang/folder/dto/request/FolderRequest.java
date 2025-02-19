package TripleS.VidiLang.folder.dto.request;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FolderRequest(
        @Schema(description = "폴더Id", example = "1") Long folderId,
        @Schema(description = "폴더명", example = "나의 불어 노트") String name,
        @Schema(description = "색상 타입 - 파스칼 케이스", example = "Red") String colorType,
        @Schema(description = "언어 타입 - 파스칼 케이스", example = "French") String languageType
) {

}
