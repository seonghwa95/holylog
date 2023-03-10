package com.holyheem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.holyheem.domain.Post;
import com.holyheem.repository.PostRepository;
import com.holyheem.request.PostCreate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.junit.jupiter.api.Assertions.*;

@AutoConfigureMockMvc
@SpringBootTest
class PostControllerTest {

    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private PostRepository postRepository;

    @BeforeEach     // 각각의 메서드전에 실행해주는 메서드 (독립적인 테스트 환경을 위해 -> 테스트가 다른 테스트에 영향을 줘선 안된다!)
    void clean() {
        postRepository.deleteAll();
    }

    @Test
    @DisplayName("/posts 요청시 Hello World를 출력한다.")
    void 테스트() throws Exception {
        // given
        // PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        PostCreate request = PostCreate.builder()   // 위의 생성자의 순서가 변경되는 경우 방지
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // json 프로세싱 해주는 라이브러리 ( request의 string들을 json 형태로 가공 )
        // 실무에서 굉장히 많이 쓰이니 반드시 기억해두자
        String json = objectMapper.writeValueAsString(request);

        // 눈으로 확인...
        System.out.println(json);

        // 글 내용

        // expects
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("{}"))
                .andDo(MockMvcResultHandlers.print()); // http 방식의 요약정보를 log로 출력해준다.

    }

    @Test
    @DisplayName("/posts 요청시 title 값은 필수다.")
    void 테스트2() throws Exception {
        // given
        // PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        PostCreate request = PostCreate.builder()   // 위의 생성자의 순서가 변경되는 경우 방지
                .content("내용입니다.")
                .build();

        // json 프로세싱 해주는 라이브러리 ( request의 string들을 json 형태로 가공 )
        // 실무에서 굉장히 많이 쓰이니 반드시 기억해두자
        String json = objectMapper.writeValueAsString(request);

        // expects
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isBadRequest())
                .andExpect(MockMvcResultMatchers.jsonPath("$.code").value("400"))
                .andExpect(MockMvcResultMatchers.jsonPath("$.message").value("잘못된 요청입니다."))
                .andExpect(MockMvcResultMatchers.jsonPath("$.validation.title").value("타이틀을 입력해주세요."))
                .andDo(MockMvcResultHandlers.print()); // http 방식의 요약정보를 log로 출력해준다.

    }
    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void 테스트3() throws Exception {
        // given
        // PostCreate request = new PostCreate("제목입니다.", "내용입니다.");
        PostCreate request = PostCreate.builder()   // 위의 생성자의 순서가 변경되는 경우 방지
                .title("제목입니다.")
                .content("내용입니다.")
                .build();

        // json 프로세싱 해주는 라이브러리 ( request의 string들을 json 형태로 가공 )
        // 실무에서 굉장히 많이 쓰이니 반드시 기억해두자
        String json = objectMapper.writeValueAsString(request);

        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then
        Assertions.assertEquals(1L, postRepository.count());    // static 메서드에 대해 알아보기

        Post post = postRepository.findAll().get(0);
        Assertions.assertEquals("제목입니다.", post.getTitle());
        Assertions.assertEquals("내용입니다.", post.getContent());

    }
    @Test
    @DisplayName("/posts 요청시 DB에 값이 저장된다.")
    void 테스트4() throws Exception {
        // when
        mockMvc.perform(MockMvcRequestBuilders.post("/posts")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"title\":\"제목입니다.\", \"content\":\"내용입니다.\"}")
                )
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print());

        // then
        Assertions.assertEquals(1L, postRepository.count());    // static 메서드에 대해 알아보기
    }
}