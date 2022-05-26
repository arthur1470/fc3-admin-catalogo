package com.fullcycle.admin.catalogo.infrastructure.api;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fullcycle.admin.catalogo.ControllerTest;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryOutput;
import com.fullcycle.admin.catalogo.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admin.catalogo.domain.category.CategoryID;
import com.fullcycle.admin.catalogo.infrastructure.category.models.CreateCategoryApiInput;
import io.vavr.API;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.Objects;

import static org.hamcrest.Matchers.equalTo;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.Mockito.times;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;

@ControllerTest(controllers = CategoryAPI.class)
class CategoryAPITest {

    @Autowired
    private MockMvc mvc;

    @MockBean
    private CreateCategoryUseCase createCategoryUseCase;

    @Autowired
    private ObjectMapper mapper;

    @Test
    void givenAValidCommand_whenCallsCreateCategory_shouldReturnCategoryId() throws Exception {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var aInput =
                new CreateCategoryApiInput(expectedName, expectedDescription, expectedIsActive);

        Mockito.when(createCategoryUseCase.execute(any()))
                .thenReturn(API.Right(CreateCategoryOutput.from("123")));

        final var request = MockMvcRequestBuilders.post("/categories")
                .contentType(MediaType.APPLICATION_JSON)
                .content(this.mapper.writeValueAsString(aInput));

        this.mvc.perform(request)
                .andDo(MockMvcResultHandlers.print())
                .andExpectAll(
                        MockMvcResultMatchers.status().isCreated(),
                        header().string("Location", "/categories/123"),
                        header().string("Content-Type", MediaType.APPLICATION_JSON_VALUE),
                        jsonPath("$.id", equalTo("123"))
                );

        Mockito.verify(createCategoryUseCase, times(1)).execute(argThat(cmd ->
                Objects.equals(expectedName, cmd.name())
                && Objects.equals(expectedDescription, cmd.description())
                && Objects.equals(expectedIsActive, cmd.isActive())
        ));
    }
}
