package ru.otus.spring.controller;

import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.*;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import ru.otus.spring.domain.dto.AuthorDto;
import ru.otus.spring.domain.dto.BookDto;
import ru.otus.spring.domain.dto.GenreDto;

import java.util.ArrayList;

@AutoConfigureMockMvc
@SpringBootTest
@Transactional
class BookControllerTest {

    public static final int BOOK_COUNT = 17;
    public static final String BOOK_NAME = "controller any book name";
    public static final String NEW_BOOK_NAME = "controller new other book name";
    public static final String AUTHOR_NAME = "controller author book name";
    public static final String GENRE_NAME = "controller genre book name";
    public static final long AUTHOR_ID = 2L;
    public static final long GENRE_ID = 3L;
    public static final long EXISTING_BOOK_ID = 4L;
    public static final long NOT_EXISTING_BOOK_ID = 256L;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void findAllInDtoTest() throws Exception {
        mockMvc.perform(
                get("/book")
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(BOOK_COUNT)))
                .andExpect(jsonPath("$[0].name").value("Puaro"));
    }

    @Test
    void getBookByIdInPathTest() throws Exception {
        mockMvc.perform(
                get(String.format("/book/%d", EXISTING_BOOK_ID))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(EXISTING_BOOK_ID));
    }


    @Test
    void getBookByIdInPathNotExistingErrorTest() throws Exception {
        mockMvc.perform(
                get(String.format("/book/%d", NOT_EXISTING_BOOK_ID))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
    }

    @Test
    void insertBookTest() throws Exception {
        BookDto bookDto = getAnyBookDto(null);
        bookDto.setName(NEW_BOOK_NAME);

        String content = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                post("/book")
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NEW_BOOK_NAME))
        ;
    }

    @Test
    void updateBookExistingTest() throws Exception {
        BookDto bookDto = getAnyBookDto(EXISTING_BOOK_ID);
        bookDto.setName(NEW_BOOK_NAME);

        String content = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                put(String.format("/book/%d", EXISTING_BOOK_ID))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
        ;

        mockMvc.perform(
                get(String.format("/book/%d", EXISTING_BOOK_ID))
        )
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(NEW_BOOK_NAME))
        ;
    }

    @Test
    void updateBookNotExistingErrorTest() throws Exception {
        BookDto bookDto = getAnyBookDto(NOT_EXISTING_BOOK_ID);

        String content = objectMapper.writeValueAsString(bookDto);

        mockMvc.perform(
                put(String.format("/book/%d", NOT_EXISTING_BOOK_ID))
                        .content(content)
                        .contentType(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
        ;
    }

    @Test
    void deleteBookExistingTest() throws Exception {
        mockMvc.perform(
                delete(String.format("/book/%d", EXISTING_BOOK_ID))
        )
                .andExpect(status().isNoContent())
        ;
    }

    @Test
    void deleteBookNotExistingErrorTest() throws Exception {
        mockMvc.perform(
                delete(String.format("/book/%d", NOT_EXISTING_BOOK_ID))
        )
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").exists());
        ;
    }

    private BookDto getAnyBookDto(Long bookId) throws JsonProcessingException {
        ObjectMapper om = new ObjectMapper();
        System.out.println(
                om.writeValueAsString(new BookDto(
                        bookId,
                        BOOK_NAME,
                        new AuthorDto(AUTHOR_ID, AUTHOR_NAME),
                        new GenreDto(GENRE_ID, GENRE_NAME),
                        new ArrayList<>()
                ))
        );
        return new BookDto(
                bookId,
                BOOK_NAME,
                new AuthorDto(AUTHOR_ID, AUTHOR_NAME),
                new GenreDto(GENRE_ID, GENRE_NAME),
                new ArrayList<>()
        );
    }
}