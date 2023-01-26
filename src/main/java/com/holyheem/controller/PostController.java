package com.holyheem.controller;

import com.holyheem.request.PostCreate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Slf4j
@RestController // 데이터 기반으로 API응답 방식으로 진행할 예정 (JSON)
public class PostController {
    // SSR -> JSP, thymeleaf, mustache, freemarker
    // SPA ->
    //      vue -> vue + SSR = nuxt.js    ( 이걸로 진행할 예정 )
    //      react -> react + SSR = next.js


    // Http Method
    // GET, POST, PUT, PATCH, DELETE, OPTIONS, HEAD, TRACE, CONNECT 각각 전부 공부해보기
    // 글 등록
    // POST Method


    // @RequestMapping(method = RequestMethod.GET, path = "/posts") -> 예전 방식
    @PostMapping("/posts")
    public Map<String, String> get(@RequestBody @Valid PostCreate params, BindingResult result) {
        log.info("params = {}", params.toString());

        /**
         * 아래와 같은 방식의 데이터 검증은 매우 별로다..
         * 1. 빡세다.
         * 2. 개발팁 -> 무언가 3번이상 반복작업을 할 때 내가 뭔가 잘못하고 있는건 아닐지 의심한다.
         * 3. 누락가능성
         * 4. 생각보다 검증해야할 것이 많다. ( 꼼꼼하지 않을 수 있다. )
         * 5. 뭔가 개발자스럽지 못하다. ( 간지X )
         */
//        String title = params.getTitle();
//        if (title == null || title.equals("")) {
//            throw new Exception("타이틀 값이 없어요!");
//        }
//
//        String content = params.getContent();
//        if (content == null || content.equals("")) {
//            // error
//        }

        /**
         * 해결책 -> parameter에 @Valid 어노테이션을 추가 / DTO에 @NotBlank를 추가한다.
         * 데이터 검증을 스프링에게 맡긴다. ㅎㅎ
         * 이 방식은 테스트를 진행할 때, 컨트롤러로 넘어오기 전에 데이터 검증을 끝내기 때문에
         * BreakPoint를 걸어 테스트를 실행하더라도 Break가 되지 않고 바로 테스트 실패가 된다.
         */


        /**
         * 값이 비었다는 에러를 내고 실행이 안되는 것에서 끝나는 것이 아니라
         * 클라이언트에게 값이 비었다고 내용을 보여주기 위해
         * BindingResult를 이용한다.
         *
         * 그러나 아래의 코드도 복잡한 편..
         * 1. 매번 메서드마다 값을 검증 해야한다.
         *      > 개발자가 까먹을 여지가 다분
         *      > 검증 부분에서 버그가 발생할 여지가 다분
         *      > 지겹다. ( 간지X )
         * 2. 응답값에 HashMap -> 응답 클래스를 만들어주는게 좋다.
         * 3. 여러개의 에러처리 힘들다.
         * 4. 세 번이상의 반복적인 작업은 피해야한다.
         */
        if (result.hasErrors()) {
            List<FieldError> fieldErrors = result.getFieldErrors();     // 에러 리스트
            FieldError firstFieldError = fieldErrors.get(0);            // 리스트의 0번째 인덱스
            String fieldName = firstFieldError.getField();              // title
            String errorMessage = firstFieldError.getDefaultMessage();  // 에러 메세지 => must not be blank
            // 에러 메세지 변경방법 NotBlank 어노테이션에 message 속성 넣어서 변경

            Map<String, String> error = new HashMap<>();
            error.put(fieldName, errorMessage);
            return error;
        }       

        return Map.of();
    }
}
