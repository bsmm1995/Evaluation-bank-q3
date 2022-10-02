package com.bp.cbe.controller;

import com.bp.cbe.domain.dto.PersonDTO;
import com.bp.cbe.domain.dto.error.ErrorResponse;
import com.bp.cbe.domain.enums.Status;
import com.bp.cbe.domain.enums.UserType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.greaterThan;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureTestDatabase
@AutoConfigureMockMvc
class PersonControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private ObjectMapper objectMapper;

    PersonDTO dto;

    @BeforeEach
    void setUp() {
        dto = new PersonDTO(1L, "Bladimir", UserType.INTERNAL);
    }

    @Test
    @DisplayName("create a person")
    void createPersonShouldHaveCreatedStatus() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders.post("/persons").contentType(MediaType.APPLICATION_JSON).content(objectMapper.writeValueAsString(dto))).andExpect(status().isCreated()).andExpect(jsonPath("$.id", is(greaterThan(0)))).andExpect(jsonPath("$.names").exists()).andReturn();

        PersonDTO result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), PersonDTO.class);

        assertNotNull(result);
        assertEquals(Status.CREATED, result.getStatus());

        mvc.perform(MockMvcRequestBuilders.get("/persons/" + result.getId()).accept(MediaType.APPLICATION_JSON)).andDo(print()).andExpect(status().isOk()).andExpect(jsonPath("$.id").exists()).andExpect(jsonPath("$.names", is("Bladimir"))).andExpect(jsonPath("$.loans").isEmpty());
    }

    @Test
    @DisplayName("should give an unsupported user type error")
    void createPerson() throws Exception {
        MvcResult mvcResult = mvc.perform(MockMvcRequestBuilders
                .post("/persons").contentType(MediaType.APPLICATION_JSON)
                .content("{\"names\":\"string\",\"type\":\"NONE\"}")).andExpect(status().is4xxClientError()).andReturn();

        ErrorResponse result = objectMapper.readValue(mvcResult.getResponse().getContentAsString(), ErrorResponse.class);

        assertNotNull(result);
        assertEquals("User type does not exist", result.getMessage());
    }
}