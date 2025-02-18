package TripleS.VidiLang.folder.dto.reponse;

import TripleS.VidiLang.folder.entity.Folder;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Builder;

@Builder
public record FolderListResponse(
        @Schema(description = "폴더명", example = "영어 공부용 노트") String name,
        @Schema(description = "색상 타입 - 파스칼 케이스", example = "Red") String colorType,
        @Schema(description = "언어 타입 - 파스칼 케이스", example = "English") String LanguageType
) {

    public static FolderListResponse from(Folder folder) {
        return FolderListResponse.builder()
                .name(folder.getName())
                .colorType(folder.getColorType().name())
                .LanguageType(folder.getLanguageType().name())
                .build();
    }
}
