package TripleS.VidiLang.folder.service;

import TripleS.VidiLang.folder.dto.reponse.FolderResponse;
import TripleS.VidiLang.folder.dto.request.FolderRequest;
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
import java.util.List;
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
    public void createFolder(Principal principal, FolderRequest folderRequest) {
        Long memberId = Long.parseLong(principal.getName());
        Member member = getMemberById(memberId);
        Folder folder = toFolderWithMember(member, folderRequest);

        folderRepository.save(folder);
    }

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<FolderResponse>> getFolderList(Principal principal) {
        Long memberId = Long.parseLong(principal.getName());
        exitsMemberById(memberId);
        List<Folder> folders = findFolderListByMemberId(memberId);
        List<FolderResponse> folderResponses = folderResponsesConverter(folders);

        return ApiResponseTemplate.<List<FolderResponse>>builder()
                .status(200)
                .success(true)
                .message("폴더 조회 성공")
                .data(folderResponses)
                .build();
    }

    @Transactional
    public void updateFolderBy(Principal principal, FolderRequest folderRequest) {
        Long memberId = Long.parseLong(principal.getName());
        exitsMemberById(memberId);
        Folder folder = findFolderById(folderRequest.folderId());

        folder.update(
                folderRequest.name(),
                ColorType.getColorTypeOfString(folderRequest.colorType()),
                LanguageType.getLanguageTypeOfString(folderRequest.languageType()));
    }

    private Folder findFolderById(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_ID_EXCEPTION,
                        ErrorCode.NOT_FOUND_ID_EXCEPTION.getMessage() + "id = " + folderId));
    }

    private void exitsMemberById(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new CustomException(ErrorCode.INVALID_ID_EXCEPTION,
                    ErrorCode.INVALID_ID_EXCEPTION.getMessage());
        }
    }

    private List<FolderResponse> folderResponsesConverter(List<Folder> folders) {
        return folders.stream()
                .map(FolderResponse::from)
                .toList();
    }

    private List<Folder> findFolderListByMemberId(Long memberId) {
        return folderRepository.findByMemberId(memberId);
    }

    private Member getMemberById(Long memberId) {
        return memberRepository.findById(memberId)
                .orElseThrow(() -> new CustomException(ErrorCode.INVALID_ID_EXCEPTION,
                        ErrorCode.INVALID_ID_EXCEPTION.getMessage() + memberId));
    }

    private Folder toFolderWithMember(Member member, FolderRequest folderRequest) {
        return Folder.builder()
                .member(member)
                .name(folderRequest.name())
                .colorType(ColorType.getColorTypeOfString(folderRequest.colorType()))
                .languageType(LanguageType.getLanguageTypeOfString(folderRequest.languageType()))
                .build();
    }
}
