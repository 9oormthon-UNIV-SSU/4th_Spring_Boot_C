package study.spring_boot_c.domain.member.api;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.Parameters;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import study.spring_boot_c.domain.member.application.MemberService;
import study.spring_boot_c.domain.member.dto.MemberResponseDTO;
import study.spring_boot_c.domain.member.exception.annotation.MemberExist;
import study.spring_boot_c.global.common.response.BaseResponse;
import study.spring_boot_c.global.error.code.status.SuccessStatus;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
@Validated
public class MemberRestController {

    private final MemberService memberService;

    @GetMapping("/example/{member-id}")
    @Operation(summary = "예시 API (여기에 API와 관련된 설명을 작성해줍니다..!")
    @ApiResponses({
            @io.swagger.v3.oas.annotations.responses.ApiResponse(responseCode = "MEMBER_200", description = "OK, 성공적으로 조회되었습니다."),
    })
    @Parameters({
            @Parameter(name = "member-id", description = "parameter에 대한 설명이 들어갑니다.")
    })
    public BaseResponse<MemberResponseDTO.Example> getMember(@PathVariable(name = "member-id") @Valid @MemberExist Long memberId){


        MemberResponseDTO.Example result = memberService.exampleMethod(memberId);

        return BaseResponse.onSuccess(SuccessStatus.MEMBER_EXAMPLE_SUCCESS, result);
    }

}
