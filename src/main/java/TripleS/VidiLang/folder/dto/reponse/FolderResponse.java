package TripleS.VidiLang.folder.dto.reponse;

import TripleS.VidiLang.folder.entity.Folder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FolderResponse(
        @Schema(description = "식별자 id", example = "1") Long id,
        @Schema(description = "폴더명", example = "영어 공부용 노트") String name,
        @Schema(description = "색상 타입 - 파스칼 케이스", example = "Red") String colorType,
        @Schema(description = "언어 타입 - 파스칼 케이스", example = "English") String languageType
) {

    public static FolderResponse from(Folder folder) {
        return FolderResponse.builder()
                .id(folder.getId())
                .name(folder.getName())
                .colorType(folder.getColorType().name())
                .languageType(folder.getLanguageType().name())
                .build();
    }
}
