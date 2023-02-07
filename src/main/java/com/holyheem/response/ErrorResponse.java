package com.holyheem.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.HashMap;
import java.util.Map;

/**
 * 이런 방식으로 하셨다고 함
 * {
 *     "code" : "400",
 *     "message" : "잘못된 요청입니다.",
 *     "validation" : {
 *         "title" : "제목을 입력해주세요",      // 피드백 필요
 *         "content" : "내용을 입력해주세요"     // 피드백 필요
 *     }
 * }
 */
@Getter
public class ErrorResponse {

    private final String code;
    private final String message;
    private Map<String, String> validation = new HashMap<>();

    @Builder
    public ErrorResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public void addValidation(String fieldName, String errorMessage) {
        this.validation.put(fieldName, errorMessage);
    }
}
