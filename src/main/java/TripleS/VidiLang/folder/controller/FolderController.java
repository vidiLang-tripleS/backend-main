package TripleS.VidiLang.folder.controller;

import TripleS.VidiLang.folder.dto.reponse.FolderResponse;
import TripleS.VidiLang.folder.dto.request.FolderRequest;
import TripleS.VidiLang.folder.service.FolderService;
import TripleS.VidiLang.global.common.dto.ApiResponseTemplate;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.security.Principal;
import java.util.List;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor(access = AccessLevel.PROTECTED)
@Tag(name = "폴더 관련 api", description = "폴더 생성, 수정, 삭제")
@RequestMapping("/api/v1/folder")
public class FolderController {

    private final FolderService folderService;

    @PostMapping("")
    @Operation(
            summary = "폴더 생성",
            description = "폴더를 생성하는 api입니다",
            responses = {
                    @ApiResponse(responseCode = "201", description = "폴더 생성 성공 성공"),
                    @ApiResponse(responseCode = "400", description = "찾을 수 없는 사용자"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            })
    public ResponseEntity<String> createFolder(Principal principal, @RequestBody FolderRequest folderRequest) {
        folderService.createFolder(principal, folderRequest);
        return ResponseEntity.status(HttpStatus.CREATED).body("폴더 생성 성공");
    }

    @PatchMapping("/{folderId}")
    @Operation(
            summary = "폴더 업데이트",
            description = "폴더 정보를 업데이트하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "폴더 업데이트 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            })
    public ResponseEntity<ApiResponseTemplate<FolderResponse>> updateFolder(Principal principal,
                                                                            @PathVariable Long folderId,
                                                                            @RequestBody FolderRequest folderRequest) {
         ApiResponseTemplate<FolderResponse> data = folderService.updateFolder(principal, folderId, folderRequest);
        return ResponseEntity.status(data.getStatus()).body(data);
    }

    @GetMapping("")
    @Operation(
            summary = "폴더 목록 조회",
            description = "사용자가 보유한 폴더 목록을 조회하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "200", description = "폴더 목록 조회 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            })
    public ResponseEntity<ApiResponseTemplate<List<FolderResponse>>> getFolderList(Principal principal) {
        ApiResponseTemplate<List<FolderResponse>> response = folderService.getFolderList(principal);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{folderId}")
    @Operation(
            summary = "폴더 삭제",
            description = "폴더를 삭제하는 API입니다.",
            responses = {
                    @ApiResponse(responseCode = "204", description = "폴더 삭제 성공"),
                    @ApiResponse(responseCode = "400", description = "잘못된 요청"),
                    @ApiResponse(responseCode = "404", description = "폴더를 찾을 수 없음"),
                    @ApiResponse(responseCode = "500", description = "서버 에러")
            })
    public ResponseEntity<Void> deleteFolder(Principal principal, @PathVariable Long folderId) {
        folderService.deleteFolderById(principal, folderId);
        return ResponseEntity.noContent().build();
    }
}
