package com.holyheem.request;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Setter
@Getter
@ToString
public class PostCreate {

    @NotBlank(message = "타이틀을 입력해주세요.")
    private String title;
    @NotBlank(message = "콘텐츠를 입력해주세요.")
    private String content;

    @Builder    // Builder 패턴에 대해 공부해보자
    public PostCreate(String title, String content) {
        this.title = title;
        this.content = content;
    }

    // 빌더의 장점
    // * 가독성이 좋다. (값 생성에 대한 유연함)
    // * 필요한 값만 받을 수 있다.(오버로딩 만드는 [빡치는]과정을 생략할 수 있다.) [오버로딩 가능한 조건 찾아보기]
    // * 객체의 불변성
    // <b>아무튼 그냥 공부해서 쓸 수 있도록 만들자</b>
}
