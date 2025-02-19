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
        Member member = getMemberById(principal.getName());
        Folder folder = toFolderWithMember(member, folderRequest);

        folderRepository.save(folder);
    }

    @Transactional(readOnly = true)
    public ApiResponseTemplate<List<FolderResponse>> getFolderList(Principal principal) {
        exitsMemberByEmail(principal.getName());
        List<Folder> folders = findFolderListByMemberId(principal.getName());
        List<FolderResponse> folderResponses = folderResponsesConverter(folders);

        return ApiResponseTemplate.<List<FolderResponse>>builder()
                .status(200)
                .success(true)
                .message("폴더 조회 성공")
                .data(folderResponses)
                .build();
    }

    @Transactional
    public ApiResponseTemplate<FolderResponse> updateFolder(Principal principal, Long folderId, FolderRequest folderRequest) {
        exitsMemberByEmail(principal.getName());
        Folder folder = findFolderById(folderId);

        folder.update(folderRequest.name(),
                ColorType.getColorTypeOfString(folderRequest.colorType()),
                LanguageType.getLanguageTypeOfString(folderRequest.languageType()));

        return ApiResponseTemplate.<FolderResponse>builder()
                .status(200)
                .success(true)
                .message("폴더 업데이트 성공")
                .data(FolderResponse.from(folder))
                .build();
    }

    @Transactional
    public void deleteFolderById(Principal principal, Long id) {
        exitsMemberByEmail(principal.getName());

        folderRepository.deleteById(id);
    }

    private Folder findFolderById(Long folderId) {
        return folderRepository.findById(folderId)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EMAIL_EXCEPTION,
                        ErrorCode.NOT_FOUND_EMAIL_EXCEPTION.getMessage() + "id = " + folderId));
    }

    private void exitsMemberByEmail(String email) {
        if (!memberRepository.existsByEmail(email)) {
            throw new CustomException(ErrorCode.INVALID_ID_EXCEPTION,
                    ErrorCode.INVALID_ID_EXCEPTION.getMessage());
        }
    }

    private List<FolderResponse> folderResponsesConverter(List<Folder> folders) {
        return folders.stream()
                .map(FolderResponse::from)
                .toList();
    }

    private List<Folder> findFolderListByMemberId(String email) {
        return folderRepository.findByMemberEmail(email);
    }

    private Member getMemberById(String email) {
        return memberRepository.findByEmail(email)
                .orElseThrow(() -> new CustomException(ErrorCode.NOT_FOUND_EMAIL_EXCEPTION,
                        ErrorCode.NOT_FOUND_EMAIL_EXCEPTION.getMessage() + email));
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
