package TripleS.VidiLang.folder.service;

import TripleS.VidiLang.folder.dto.FolderCreateRequestDto;
import TripleS.VidiLang.folder.entity.ColorType;
import TripleS.VidiLang.folder.entity.Folder;
import TripleS.VidiLang.folder.entity.LanguageType;
import TripleS.VidiLang.folder.repository.FolderRepository;
import TripleS.VidiLang.global.common.dto.ApiResponseTemplate;
import TripleS.VidiLang.global.exception.ErrorCode;
import TripleS.VidiLang.global.exception.model.CustomException;
import TripleS.VidiLang.member.entity.Member;
import TripleS.VidiLang.member.repository.MemberRepository;
import java.security.Principal;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
public class FolderService {

    private final FolderRepository folderRepository;
    private final MemberRepository memberRepository;

    @Transactional
    public void createFolder(Principal principal, FolderCreateRequestDto folderCreateRequestDto) {
        Long memberId = Long.parseLong(principal.getName());
        Member member = getMemberById(memberId);
        Folder folder = saveFolder(member, folderCreateRequestDto);

        folderRepository.save(folder);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID_EXCEPTION,
                        ErrorCode.INVALID_ID_EXCEPTION.getMessage() + memberId));
    }

    private Folder saveFolder(Member member, FolderCreateRequestDto folderCreateRequestDto) {
        return Folder.builder()
                .member(member)
                .name(folderCreateRequestDto.getName())
                .colorType(ColorType.getColorTypeOfString(folderCreateRequestDto.getColorType()))
                .languageType(LanguageType.getLanguageTypeOfString(folderCreateRequestDto.getLanguageType()))
                .build();
    }
}
