package TripleS.VidiLang.folder.dto.request;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderCreateRequest {

    private String name;
    private String colorType;
    private String languageType;
}
