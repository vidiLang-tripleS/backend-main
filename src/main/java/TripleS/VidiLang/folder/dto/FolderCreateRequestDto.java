package TripleS.VidiLang.folder.dto;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderCreateRequestDto {

    private String name;
    private String colorType;
    private String languageType;
}
